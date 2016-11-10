package com.flytxt.compiler;

import org.junit.Assert;
import org.junit.Test;

import com.flytxt.tp.compiler.RealtimeCompiler;

public class RealtimeCompilerTest {

	@Test
	public void test1() throws Exception {
		StringBuffer sourceCode = new StringBuffer();

		sourceCode.append("package org.fly;\n");
		sourceCode.append("public class HelloClass {\n");
		sourceCode.append("   public String hello() { return \"hello world\"; }");
		sourceCode.append("}");
		
		byte[] bytes = RealtimeCompiler.compileToBytes("org.fly.HelloClass", sourceCode.toString());
		Class<?> helloClass = RealtimeCompiler.getClass("org.fly.HelloClass"); 
		Assert.assertNotNull(helloClass);
		Assert.assertEquals(1, helloClass.getDeclaredMethods().length);
	}

}
