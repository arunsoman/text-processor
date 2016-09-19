package com.flytxt.parser.compiler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.flytxt.parser.compiler.parser.Parser;

@Component
@ComponentScan
public class Utils {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public String createFile(final String loc, final String content, final String fileName) throws IOException {
        logger.debug("createFile(loc=" + loc + "fileName=" + fileName + ")");
        final Path folder = createDir(loc);
        final Path file = Paths.get(folder.toString() + "/" + fileName);
        final OpenOption[] options = { StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING };
        Files.write(file, content.getBytes(), options);
        return file.toString();
    }

    private Path createDir(final String loc) throws IOException {
        final Path folder = Paths.get(loc);
        if (!Files.exists(folder)) {
            Files.createDirectories(folder);
        }
        return folder;
    }

    public String complie(final String src, final String dest) throws Exception {
        logger.debug("compile(src=" + src + " Dest=" + dest + ")");
        try {
            createDir(dest);
        } catch (final IOException e) {
            throw new Exception(e);
        }
        final JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        final StandardJavaFileManager sjfm = javaCompiler.getStandardFileManager(null, null, null);
        final DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        final List<String> optionList = new ArrayList<String>();
        // set compiler's classpath to be same as the runtime's
        optionList.addAll(Arrays.asList("-classpath", System.getProperty("java.class.path")));
        optionList.addAll(Arrays.asList("-d", dest));
        final File[] javaFiles = new File[] { new File(src) };

        final StringWriter bos = new StringWriter();
        final CompilationTask compilationTask = javaCompiler.getTask(bos, null, null, optionList, null, sjfm.getJavaFileObjects(javaFiles));
        if (!compilationTask.call()) {
            final Locale myLocale = Locale.getDefault();
            final StringBuilder msg = new StringBuilder();
            msg.append("Cannot compile to Java bytecode:");
            for (final Diagnostic<? extends JavaFileObject> err : diagnostics.getDiagnostics()) {
                msg.append('\n');
                msg.append(err.getKind());
                msg.append(": ");
                if (err.getSource() != null) {
                    msg.append(err.getSource().getName());
                }
                msg.append(':');
                msg.append(err.getLineNumber());
                msg.append(": ");
                msg.append(err.getMessage(myLocale));
            }
            throw new Exception(msg.toString() + "\n" + bos.toString());
        }
        return null;
    }

    public String createJavaContent(final String script) {
        final Parser p = new Parser();
        new ScriptReader().read(script, p);
        final String javaContent = p.createProcessClass();
        return javaContent;
    }

    public void createJar(final String loc, final String dest) throws IOException {
        logger.debug("JAr---" + loc + " : " + dest);
        final Path destP = Paths.get(dest).getParent();
        if (!Files.exists(destP)) {
            Files.createDirectories(destP);
        }
        final FileOutputStream fout = new FileOutputStream(dest);
        final JarOutputStream jarOut = new JarOutputStream(fout);
        listFiles(Paths.get(loc), jarOut, loc.length(), true);
        jarOut.close();
        fout.close();
    }

    public void listFiles(final Path path, final JarOutputStream jarOut, final int root, final boolean isParent) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (final Path entry : stream) {
                final boolean isDirectory = Files.isDirectory(entry);
                if (isDirectory) {
                    listFiles(entry, jarOut, root, false);
                    if (isParent) {
                        return;
                    }
                }
                final String folderName = entry.getParent().toString().substring(root);
                if (isDirectory) {
                    logger.debug("zip ; " + folderName + "/");
                    jarOut.putNextEntry(new ZipEntry(folderName + "/"));
                } else {
                    logger.debug("zip ; " + entry.toString().substring(root));
                    jarOut.putNextEntry(new ZipEntry(entry.toString().substring(root)));
                    logger.debug("read ; " + entry.toString());
                    jarOut.write(Files.readAllBytes(entry));
                    jarOut.closeEntry();
                }
            }
        }
    }
}
