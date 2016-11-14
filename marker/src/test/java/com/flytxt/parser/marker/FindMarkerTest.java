package com.flytxt.parser.marker;

import org.junit.Test;

import com.flytxt.tp.marker.FindMarker;

import junit.framework.Assert;

public class FindMarkerTest {
	FindMarker fm = new FindMarker();
	@Test
	public void test0(){
		String str = "12345";
		byte[] data = str.getBytes();
		byte[] token = "^".getBytes();
		Assert.assertEquals(str.indexOf("^"), fm.findPreMarker(token[0], 0, data.length, data));
	}
	@Test
	public void test1(){
		String str = "^123^45";
		byte[] data = str.getBytes();
		byte[] token = "^".getBytes();
		Assert.assertEquals(str.indexOf("^"), fm.findPreMarker(token[0], 0, data.length, data));
	}
	@Test
	public void test2(){
		String str = "123^45";
		byte[] data = str.getBytes();
		byte[] token = "^".getBytes();
		Assert.assertEquals(str.indexOf("^"), fm.findPreMarker(token[0], 0, data.length, data));
	}
	@Test
	public void test3(){
		String str = "12345^";
		byte[] data = str.getBytes();
		byte[] token = "^".getBytes();
		Assert.assertEquals(str.indexOf("^"), fm.findPreMarker(token[0], 0, data.length, data));
	}
	@Test
	public void test4(){
		String str = "1230^^45";
		byte[] data = str.getBytes();
		byte[] token = "^^".getBytes();
		Assert.assertEquals(str.indexOf("^^"), fm.findPreMarker(token[0], 0, data.length, data));
	}
	@Test
	public void test5(){
		String str = "^^1230^^45";
		byte[] data = str.getBytes();
		byte[] token = "^^".getBytes();
		Assert.assertEquals(str.indexOf("^^"), fm.findPreMarker(token[0], 0, data.length, data));
	}
	@Test
	public void test6(){
		String str = "123045^^";
		byte[] data = str.getBytes();
		byte[] token = "^^".getBytes();
		Assert.assertEquals(str.indexOf("^^"), fm.findPreMarker(token[0], 0, data.length, data));
	}
	@Test
	public void test7(){
		String str = "123045";
		byte[] data = str.getBytes();
		byte[] token = "^^".getBytes();
		Assert.assertEquals(str.indexOf("^^"), fm.findPreMarker(token[0], 0, data.length, data));
	}

}
