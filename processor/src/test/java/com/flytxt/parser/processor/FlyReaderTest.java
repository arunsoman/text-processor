package com.flytxt.parser.processor;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.LinkOption;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;

import com.flytxt.tp.processor.FlyReader;
import com.flytxt.tp.processor.LineProcessor;

import test.TestScript;

public class FlyReaderTest {
	FlyReader fr = new FlyReader();
	Method processFile;
	java.nio.file.Path path;
	@Before
	public void init(){
		LineProcessor lp = new TestScript();
        lp.getMf().getCurrentObject().init("", "");
        lp.init("TestScript", System.currentTimeMillis());
		fr.set("DummyFolder", lp);
		try {
			processFile = fr.getClass().getDeclaredMethod("processFile", Path.class);
			processFile.setAccessible(true);
			
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource("test-data").getFile());
			path = file.toPath().toRealPath(LinkOption.NOFOLLOW_LINKS);
			System.out.println(path.getFileName());
		} catch (NoSuchMethodException | SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test(){
		try {
			processFile.invoke(fr, path);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
