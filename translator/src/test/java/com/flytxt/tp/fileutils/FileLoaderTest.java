package com.flytxt.tp.fileutils;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.junit.Test;

import com.flytxt.tp.fileutils.loader.FileLoader;
import com.flytxt.tp.fileutils.loader.Loader;
import com.flytxt.tp.lookup.Search;
import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.marker.MarkerFactory;

import junit.framework.Assert;

public class FileLoaderTest {
	String str = "Hello World|World\nWallnut|fruit";
	
	@Test
	public void testPositive(){
		String newFile = "/tmp/test-load";
		BufferedReader buff;
		File f = null;
		try {
			f = new File(newFile);
			FileWriter fw = new FileWriter(f.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(str);
			bw.close();
			MarkerFactory mf = new MarkerFactory();
			final Search<Marker> object = new Search<>(mf);
			Loader loader = new FileLoader(newFile, mf);
			loader.load(object);
			final Marker marker = object.get("lln".getBytes());
	        assertEquals("fruit", marker.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		f.delete();
	}
}
