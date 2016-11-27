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
		final StringBuilder builder = new StringBuilder();
		builder.append(System.getProperty("java.class.path"));
		final File folder = new File(SHARED_LIB_FOLDER);

		if (folder.isDirectory()) {
			final File[] files = folder.listFiles();
			if(files !=null){
				for (final File f : files) {
					builder.append(":");
					builder.append(SHARED_LIB_FOLDER).append("/").append(f.getName());
				}
			}
		}
		optionList.add("-classpath");
		System.out.println(builder.toString());
		optionList.add(builder.toString());

	}

	public static byte[] compileToBytes(final String className, final String sourceCodeInText) throws Exception {

		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println(sourceCodeInText);
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		final FlyClassLoader cl = new FlyClassLoader(Thread.currentThread().getContextClassLoader());
		final FlyJavaFileObject sourceCode = new FlyJavaFileObject(className, sourceCodeInText);
		final CompiledCode compiledCode = new CompiledCode(className);
		final Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(sourceCode);
		final DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
		final FlyFileManager fileManager = new FlyFileManager(javac.getStandardFileManager(null, null, null), compiledCode,
				cl);
		final JavaCompiler.CompilationTask task = javac.getTask(null, fileManager, diagnostics, optionList, null,
				compilationUnits);
		if (!task.call()) {
			final StringBuilder sb = new StringBuilder();
			for (final Diagnostic<?> diagnostic : diagnostics.getDiagnostics()) {
				sb.append(String.format("Error on line %d in %s", diagnostic.getLineNumber(), diagnostic));
			}
			// logger.info(sourceCodeInText);
			// log.info(sourceCodeInText);
			// log.info(sb.toString());
			throw new Exception(sb.toString());
		}
		return cl.getBytes(className);
	}

	public static Class<?> getClass(final String className) throws ClassNotFoundException {

		final FlyClassLoader cl = new FlyClassLoader(Thread.currentThread().getContextClassLoader());
		return cl.findClass(className);
	}

}
