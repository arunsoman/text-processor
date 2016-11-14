package com.flytxt.tp.compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;


public class RealtimeCompiler {

    static JavaCompiler javac = ToolProvider.getSystemJavaCompiler();

    /*  private static org.slf4j.Logger logger = LoggerFactory.getLogger(RealtimeCompiler.class);

    private static org.slf4j.Logger javaLogger = LoggerFactory.getLogger("applicationLog");*/

    public static byte[] compileToBytes(String className, String sourceCodeInText) throws Exception {

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(sourceCodeInText);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        FlyClassLoader cl = new FlyClassLoader( Thread.currentThread().getContextClassLoader());
        FlyJavaFileObject sourceCode = new FlyJavaFileObject(className, sourceCodeInText);
        CompiledCode compiledCode = new CompiledCode(className);
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(sourceCode);
        final DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        final List<String> optionList = new ArrayList<>();
//        System.out.println("CLASSPATH:" + System.getProperty("java.class.path"));
//        String location = System.getProperty("user.dir");
//        System.out.println(location);
        // optionList.addAll(Arrays.asList("-classpath", System.getProperty("java.class.path")));
        optionList.addAll(Arrays.asList("-classpath", "./target/tp-web.jar:" + System.getProperty("java.class.path")));

        FlyFileManager fileManager = new FlyFileManager(javac.getStandardFileManager(null, null, null), compiledCode, cl);
        JavaCompiler.CompilationTask task = javac.getTask(null, fileManager, diagnostics, optionList, null, compilationUnits);
        if (!task.call()) {
            StringBuilder sb = new StringBuilder();
            for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics())
                sb.append(String.format("Error on line %d in %s", diagnostic.getLineNumber(), diagnostic));
            // logger.info(sourceCodeInText);
            // log.info(sourceCodeInText);
            //  log.info(sb.toString());
            throw new Exception(sb.toString());
        }
        return cl.getBytes(className);
    }

    public static Class<?> getClass(String className) throws ClassNotFoundException {

        FlyClassLoader cl = new FlyClassLoader(Thread.currentThread().getContextClassLoader());
        return cl.findClass(className);
    }

}
