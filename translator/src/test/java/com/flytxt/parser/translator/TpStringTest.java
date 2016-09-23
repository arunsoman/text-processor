package com.flytxt.parser.translator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.flytxt.parser.marker.Marker;

public class TpStringTest {

	TpString tpString;

	
	
	@Before
	public void before() {
		tpString = new TpString();
		
	}

	@Test
	public void testToUpperCase() {
		Marker mocker=new Marker();
		mocker.index=0;
		mocker.length="abc".length();
		byte[] uppercase = tpString.toUpperCase("abc".getBytes(), mocker, null);
	 
		Assert.assertArrayEquals("Error in converting to lower case",uppercase, "ABC".getBytes());
	}
	
	@Test
	public void testToLowerCase() {
		Marker mocker=new Marker();
		mocker.index=0;
		mocker.length="abc".length();
		byte[] lowerCase = tpString.toLowerCase("ABC".getBytes(), mocker, null);
	
		Assert.assertArrayEquals("Error in converting to lower case",lowerCase, "abc".getBytes());
	}
	
	@Test
	public void testToTitleCase() {
		Marker mocker=new Marker();
		mocker.index=0;
		mocker.length="asdsad".length();
		byte[] lowerCase = tpString.toTitleCase("asdsad".getBytes(), mocker, null);
	   
		Assert.assertArrayEquals("Error in converting to lower case",lowerCase, "Asdsad".getBytes());
	}
	
	@Test
	public void testLTrim() {
		Marker mocker=new Marker();
		mocker.index=0;
		mocker.length="asdsad".length();
		byte[] lowerCase = tpString.toTitleCase("asdsad".getBytes(), mocker, null);
	   
		Assert.assertArrayEquals("Error in converting to lower case",lowerCase, "Asdsad".getBytes());
	}
	
	@Test
	public void testRTrim() {
		Marker mocker=new Marker();
		mocker.index=0;
		mocker.length="asdsad".length();
		byte[] lowerCase = tpString.toTitleCase("asdsad".getBytes(), mocker, null);
	  
		Assert.assertArrayEquals("Error in converting to lower case",lowerCase, "Asdsad".getBytes());
	}

}
