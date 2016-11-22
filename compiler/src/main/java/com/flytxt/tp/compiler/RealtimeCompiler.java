package com.flytxt.tp.compiler;

import java.io.File;
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
	private static final String SHARED_LIB_FOLDER = "/shared-lib";

	private static final List<String> optionList = new ArrayList<>();

	static {
		StringBuilder builder = new StringBuilder();
		builder.append(System.getProperty("java.class.path"));
		File folder = new File(SHARED_LIB_FOLDER);
		if (folder != null) {
			if (folder.isDirectory()) {
				File[] files = folder.listFiles();
				for (File f : files) {
					builder.append(":");
					builder.append(SHARED_LIB_FOLDER).append("/").append(f.getName());
				}
			}
			optionList.add("-classpath");
			System.out.println(builder.toString());
			optionList.add(builder.toString());
		}
	}

	public static byte[] compileToBytes(String className, String sourceCodeInText) throws Exception {

		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println(sourceCodeInText);
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		FlyClassLoader cl = new FlyClassLoader(Thread.currentThread().getContextClassLoader());
		FlyJavaFileObject sourceCode = new FlyJavaFileObject(className, sourceCodeInText);
		CompiledCode compiledCode = new CompiledCode(className);
		Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(sourceCode);
		final DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
		FlyFileManager fileManager = new FlyFileManager(javac.getStandardFileManager(null, null, null), compiledCode,
				cl);
		JavaCompiler.CompilationTask task = javac.getTask(null, fileManager, diagnostics, optionList, null,
				compilationUnits);
		if (!task.call()) {
			StringBuilder sb = new StringBuilder();
			for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics())
				sb.append(String.format("Error on line %d in %s", diagnostic.getLineNumber(), diagnostic));
			// logger.info(sourceCodeInText);
			// log.info(sourceCodeInText);
			// log.info(sb.toString());
			throw new Exception(sb.toString());
		}
		return cl.getBytes(className);
	}

	public static Class<?> getClass(String className) throws ClassNotFoundException {

		FlyClassLoader cl = new FlyClassLoader(Thread.currentThread().getContextClassLoader());
		return cl.findClass(className);
	}

}
