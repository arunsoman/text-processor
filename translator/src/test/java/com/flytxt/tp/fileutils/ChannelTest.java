package com.flytxt.tp.fileutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.junit.Test;

import junit.framework.Assert;

public class ChannelTest {
	
	@Test
	public void testPositiveCp(){
		BufferedReader buff;
		try(Channel cpChannel = new ClasspathChannel()) {
			buff = cpChannel.open("searchdata.csv");
			Assert.assertEquals(true, buff.ready());
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@Test
	public void testPositiveDisk(){
		String newFile = "/tmp/test-disk";
		BufferedReader buff;
		File f = null;
		try (Channel diskChannel = new DiskChannel()){
			f = new File(newFile);
			FileWriter fw = new FileWriter(f.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("Test");
			bw.close();
			buff = diskChannel.open(newFile);
			Assert.assertEquals(true, buff.ready());
		} catch (Exception e) {
			Assert.fail();
		}
		f.delete();
	}
}
