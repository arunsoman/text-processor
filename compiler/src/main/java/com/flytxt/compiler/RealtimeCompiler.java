package com.flytxt.compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import org.slf4j.LoggerFactory;

public class RealtimeCompiler {
    static JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
    static FlyClassLoader cl = new FlyClassLoader(ClassLoader.getSystemClassLoader());
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(RealtimeCompiler.class);
    private static org.slf4j.Logger javaLogger = LoggerFactory.getLogger("applicationLog");

    public static byte[] compileToBytes(String className, String sourceCodeInText) throws Exception {
        FlyJavaFileObject sourceCode = new FlyJavaFileObject(className, sourceCodeInText);
        CompiledCode compiledCode = new CompiledCode(className);
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(sourceCode);
        final DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        final List<String> optionList = new ArrayList<>();
        optionList.addAll(Arrays.asList("-classpath", System.getProperty("java.class.path")));

        FlyFileManager fileManager = new FlyFileManager(javac.getStandardFileManager(null, null, null),
                compiledCode, cl);
        JavaCompiler.CompilationTask task = javac.getTask(null, fileManager,  diagnostics, optionList, null, compilationUnits);
        if (!task.call()) {
            StringBuilder sb = new StringBuilder();
            for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics())
                sb.append(String.format("Error on line %d in %s", diagnostic.getLineNumber(), diagnostic));
            //            logger.info(sourceCodeInText);
            javaLogger.info(sourceCodeInText);
            logger.info(sb.toString());
            throw new Exception(sb.toString());
        }
        return cl.getBytes(className);
    }

    public static Class<?> getClass(String className) throws ClassNotFoundException{
        return cl.findClass(className);
    }

}
