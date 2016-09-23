package com.flytxt.parser.translator;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;

public class TpStringTest {

	TpString tpString;
	MarkerFactory mf;
	
	@Before
	public void before() {
		mf = new MarkerFactory();
		tpString = new TpString();
		
	}
	private Marker getMarer(String str){
		Marker mocker=new Marker();
		mocker.index=0;
		mocker.length=str.length();
		return mocker;
	}
	@Test
	public void testToUpperCase() {
		String str = "abc";
		byte[] data = str.getBytes();
		Marker mocker = getMarer(str);
		Marker result = tpString.toUpperCase(data, mocker, mf);
		assertEquals(str.toUpperCase(), result.toString(result.getData()));
	}
	
	@Test
	public void testToLowerCase() {
		String str = "ABC";
		byte[] data = str.getBytes();
		Marker mocker = getMarer(str);
		Marker result = tpString.toLowerCase(data, mocker, mf);
		assertEquals(str.toLowerCase(), result.toString(result.getData()));
	}
	
	@Test
	public void testToTitleCase() {
		String str = "abc";
		byte[] data = str.getBytes();
		Marker mocker = getMarer(str);
		Marker result = tpString.toTitleCase(data, mocker, mf);
		assertEquals("Abc", result.toString(result.getData()));
	}
	
	@Test
	public void testLTrim() {
		String str = "  abc ";
		byte[] data = str.getBytes();
		Marker mocker = getMarer(str);
		Marker result = tpString.lTrim(data, mocker, mf);
		assertEquals("abc ", result.toString(data));
	}
	
	@Test
	public void testRTrim() {
		String str = "  abc ";
		byte[] data = str.getBytes();
		Marker mocker = getMarer(str);
		Marker result = tpString.rTrim(data, mocker, mf);
		assertEquals("  abc", result.toString(data));
	}

	@Test
	public void testLTrimWithAllSpaces() {
		String str = "   ";
		byte[] data = str.getBytes();
		Marker mocker = getMarer(str);
		Marker result = tpString.lTrim(data, mocker, mf);
		assertEquals(0, result.length);
	}
	
	@Test
	public void testRTrimWithAllSpaces() {
		String str = "    ";
		byte[] data = str.getBytes();
		Marker mocker = getMarer(str);
		Marker result = tpString.rTrim(data, mocker, mf);
		assertEquals(0, result.length);
	}
	

	@Test
	public void testTrimWithAllSpacesEven() {
		String str = "    ";
		byte[] data = str.getBytes();
		Marker mocker = getMarer(str);
		Marker result = tpString.trim(data, mocker, mf);
		assertEquals(0, result.length);
	}
	
	@Test
	public void testTrimWithAllSpacesOdd() {
		String str = "     ";
		byte[] data = str.getBytes();
		Marker mocker = getMarer(str);
		Marker result = tpString.trim(data, mocker, mf);
		assertEquals(0, result.length);
	}

	@Test
	public void testTrimWithSpacesEven() {
		String str = "  r  ";
		byte[] data = str.getBytes();
		Marker mocker = getMarer(str);
		Marker result = tpString.trim(data, mocker, mf);
		assertEquals(str.trim(), result.toString(data));
	}
	
	@Test
	public void testTrimWithSpacesOdd() {
		String str = "    r ";
		byte[] data = str.getBytes();
		Marker mocker = getMarer(str);
		Marker result = tpString.trim(data, mocker, mf);
		assertEquals(str.trim(), result.toString(data));
	}


}