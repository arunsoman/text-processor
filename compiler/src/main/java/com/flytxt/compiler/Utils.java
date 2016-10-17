package com.flytxt.compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

import com.flytxt.parser.marker.LineProcessor;

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
        if (!Files.exists(folder))
            Files.createDirectories(folder);
        return folder;
    }

    public LineProcessor loadClass(String dir, String className) throws MalformedURLException, Exception {
        File file = new File(dir);
        URL url = file.toURI().toURL();
        URL[] urls = new URL[] { url };
        URLClassLoader loader = new URLClassLoader(urls);
        final Class<LineProcessor> loadClass = (Class<LineProcessor>) loader.loadClass(className);
        LineProcessor lp = loadClass.getDeclaredConstructor(String.class, String.class).newInstance("/tmp/java/INReacharge","(.*)");
        loader.close();
        return lp;
    }

    public String compile(final String src, final String dest) throws Exception {
        logger.debug("compile(src=" + src + " Dest=" + dest + ")");
        try {
            createDir(dest);
        } catch (final IOException e) {
            throw new Exception(e);
        }
        final JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        final StandardJavaFileManager sjfm = javaCompiler.getStandardFileManager(null, null, null);
        final DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        final List<String> optionList = new ArrayList<>();
        // set compiler's classpath to be same as the runtime's
        optionList.addAll(Arrays.asList("-classpath", System.getProperty("java.class.path")));
        optionList.addAll(Arrays.asList("-d", dest));
        List<JavaFileObject> fileList = null;
        if (Files.isDirectory(Paths.get(src)))
            fileList = getFileList(new File(src, "."), sjfm);
        else {
            File f = new File(src);
            fileList = getFileList(new File(f.getParent(), f.getName()), sjfm);
        }

        CompilationTask compilerTask = javaCompiler.getTask(null, sjfm, diagnostics, optionList, null, fileList);
        if (!compilerTask.call()) {
            StringBuilder sb = new StringBuilder();
            for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics())
                sb.append(String.format("Error on line %d in %s", diagnostic.getLineNumber(), diagnostic));
            throw new Exception(sb.toString());
        }

        return null;
    }

    public void createJar(final String loc, final String dest) throws IOException {
        logger.debug("create jar---" + loc + " : " + dest);
        final Path destP = Paths.get(dest).getParent();
        if (!Files.exists(destP))
            Files.createDirectories(destP);
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
                    if (isParent)
                        return;
                }
                final String folderName = entry.getParent().toString().substring(entry.getParent().toString().lastIndexOf('/') + 1);
                if (isDirectory)
                    // logger.debug("zip ; "+folderName+"/");
                    jarOut.putNextEntry(new ZipEntry(folderName + "/"));
                else {
                    // logger.debug("zip ; "+entry.toString().substring(root));
                    jarOut.putNextEntry(new ZipEntry(entry.toString().substring(root)));
                    // logger.debug("read ; "+entry.toString());
                    jarOut.write(Files.readAllBytes(entry));
                    jarOut.closeEntry();
                }
            }
        }
    }

    public String createSingleVM() throws IOException {
        return readFile("Script.lp");

    }

    public String folderEvent() throws IOException {
        return readFile("Script2.lp");
    }

    public String readFile(String fileName) throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder content = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null)
            content.append(line).append("\n");
        reader.close();

        return content.toString();
    }

    public String testRunLp(LineProcessor lp, String[] data) throws IOException {
        lp.init("");
        for (String line : data) {
            byte[] datum = line.getBytes();
            lp.process(datum, 0, datum.length);
        }
        return lp.done();
    }

    private JavaFileObject readJavaObject(File file, StandardJavaFileManager fileManager) {
        Iterable<? extends JavaFileObject> javaFileObjects = fileManager.getJavaFileObjects(file);
        Iterator<? extends JavaFileObject> it = javaFileObjects.iterator();
        if (it.hasNext())
            return it.next();
        throw new RuntimeException("Could not load " + file.getAbsolutePath() + " java file object");
    }

    private List<JavaFileObject> getFileList(File dir, StandardJavaFileManager fileManager) {
        List<JavaFileObject> javaObjects = new LinkedList<>();
        File[] files = dir.isDirectory() ? dir.listFiles() : new File[] { dir };
        for (File file : files)
            if (file.isDirectory())
                javaObjects.addAll(getFileList(file, fileManager));
            else if (file.isFile() && file.getName().toLowerCase().endsWith(".java"))
                javaObjects.add(readJavaObject(file, fileManager));
        return javaObjects;
    }

    public Map<String, String> parseScript(String script) {
        String[] split = script.split("\n");
        Map<String, String> scriptMap = new HashMap<>();
        for (String key : split) {
            int indexOf = key.indexOf("=");
            scriptMap.put(key.substring(0, indexOf).trim(), key.substring(indexOf + 1, key.length()));
        }
        return scriptMap;
    }

    public String replaceWithConsoleStore(String absProcessor) {
        if (absProcessor.contains("hdfsStore") || absProcessor.contains("HdfsStore")) {
            int toBeReplacedEnd = absProcessor.indexOf("Store("), toBeReplacedStart;
            for (toBeReplacedStart = toBeReplacedEnd; toBeReplacedStart > 0; toBeReplacedStart--)
                if (absProcessor.charAt(toBeReplacedStart) == ' ')
                    break;
            String toBeReplaced = absProcessor.substring(toBeReplacedStart + 1, toBeReplacedEnd);
            return absProcessor.replace(toBeReplaced, "Console");
        }
        return absProcessor;

    }
}
