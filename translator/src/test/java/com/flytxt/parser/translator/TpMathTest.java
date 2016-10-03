package com.flytxt.parser.translator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;

public class TpMathTest {
	TpMath tpMath = new TpMath();
	MarkerFactory mf = new MarkerFactory();
	
	@Before
	public void setUp() throws Exception {
	}
	
	private Marker getMarker(String str){
		Marker mocker=new Marker();
		mocker.index=0;
		mocker.length=str.length();
		return mocker;
	}

	@Test
	public final void testAbs() {
		String str = "-98.78";
		Marker m = getMarker(str);
		Marker actual = tpMath.abs(str.getBytes(), m, mf);
		assertEquals(String.valueOf(Math.abs(Double.parseDouble(str))), actual.toString(str.getBytes()));
	}

	@Test
	public final void testLessThan() {
		String str1 = "-98.78";
		Marker m1 = getMarker(str1);
		String str2 = "-99.78";
		Marker m2 = getMarker(str2);
		assertEquals(Double.parseDouble(str1)<Double.parseDouble(str2),
				tpMath.lessThan(str1.getBytes(), m1, str2.getBytes(), m2, mf));
	}

	@Test
	public final void testLessEqThan() {
		String str1 = "-98.78";
		Marker m1 = getMarker(str1);
		String str2 = "-98.78";
		Marker m2 = getMarker(str2);
		assertEquals(Double.parseDouble(str1)<=Double.parseDouble(str2),
		             tpMath.lessEqThan(str1.getBytes(), m1, str2.getBytes(), m2, mf));
	}

	@Test
	public final void testGreaterEqThan() {
		String str1 = "99.78";
		Marker m1 = getMarker(str1);
		String str2 = "98.78";
		Marker m2 = getMarker(str2);
		assertEquals(Double.parseDouble(str1)>=Double.parseDouble(str2),
				tpMath.greaterEqThan(str1.getBytes(), m1, str2.getBytes(), m2, mf));
	}

	@Test
	public final void testGreaterThan() {
		String str1 = "98.78";
		Marker m1 = getMarker(str1);
		String str2 = "98.78";
		Marker m2 = getMarker(str2);
		assertEquals(tpMath.lessEqThan(str1.getBytes(), m1, str2.getBytes(), m2, mf), true);
	}

	@Test
	public final void testSubLong() {
		String str1 = "988";
		Marker m1 = getMarker(str1);
		String str2 = "9878";
		Marker m2 = getMarker(str2);
		Marker res = tpMath.subLong(str1.getBytes(), m1, str2.getBytes(), m2, mf);
		String str3 = String.valueOf(Long.parseLong(str1)-Long.parseLong(str2));
		assertEquals(str3, res.toString(res.getData()));
	}

	@Test
	public final void testSubDouble() {
		String str1 = "98.78";
		Marker m1 = getMarker(str1);
		String str2 = "98.78";
		Marker m2 = getMarker(str2);
		Marker res = tpMath.subDouble(str1.getBytes(), m1, str2.getBytes(), m2, mf);
		String str3 = String.valueOf(Double.parseDouble(str1)-Double.parseDouble(str2));
		assertEquals(str3, res.toString(res.getData()));
	}

	@Test
	public final void testAddLong() {
		String str1 = "9878";
		Marker m1 = getMarker(str1);
		String str2 = "9878";
		Marker m2 = getMarker(str2);
		Marker res = tpMath.addLong(str1.getBytes(), m1, str2.getBytes(), m2, mf);
		String str3 = String.valueOf(Long.parseLong(str1)+Long.parseLong(str2));
		assertEquals(str3, res.toString(res.getData()));
	}

	@Test
	public final void testAddDouble() {
		String str1 = "98.78";
		Marker m1 = getMarker(str1);
		String str2 = "98.78";
		Marker m2 = getMarker(str2);
		Marker res = tpMath.addDouble(str1.getBytes(), m1, str2.getBytes(), m2, mf);
		String str3 = String.valueOf(Double.parseDouble(str1)+Double.parseDouble(str2));
		assertEquals(str3, res.toString(res.getData()));
	}

	@Test
	public final void testSub() {
	}

	@Test
	public final void testAdd() {
	}
/*
	@Test
	public final void testCeil() {
		String str1 = "98.78";
		Marker m1 = getMarker(str1);
		Marker res = tpMath.ceil(str1.getBytes(), m1,  mf);
		String str3 = String.valueOf(Double.parseDouble(str1)+Double.parseDouble(str2));
		assertEquals(str3, res.toString(res.getData()));
	}

	@Test
	public final void testFloor() {
		String str1 = "98.78";
		Marker m1 = getMarker(str1);
	}

	@Test
	public final void testRound() {
		String str1 = "98.78";
		Marker m1 = getMarker(str1);
	}
*/
	@Test
	public final void testEq() {
		String str1 = "98.78";
		Marker m1 = getMarker(str1);
		String str2 = "98.78";
		Marker m2 = getMarker(str2);
		assertEquals(tpMath.eq(str1.getBytes(), m1, str2.getBytes(), m2, mf), true);
	}

	@Test
	public final void testExtractDecimalIntegerPart() {
		String str1 = "98.78";
		Marker m1 = getMarker(str1);
		Marker res = tpMath.extractDecimalIntegerPart(str1.getBytes(), m1, mf);
		assertEquals(str1.substring(0, str1.indexOf('.')), res.toString(str1.getBytes()));
	}

	@Test
	public final void testExtractDecimalFractionPart() {
		String str1 = "98.78";
		Marker m1 = getMarker(str1);
		Marker res = tpMath.extractDecimalFractionPart(str1.getBytes(), m1, mf);
		assertEquals(str1.substring(str1.indexOf('.')), res.toString(str1.getBytes()));
	}

	@Test
	public final void testIsNumber() {
		String str1 = "98.78";
		Marker m1 = getMarker(str1);
		boolean res = tpMath.isNumber(str1.getBytes(), m1, mf);
		assertEquals(true, res);
	}

	@Test
	public final void testAsByteArrayDouble() {
		double input1 = 98.78;
		byte[] bDoubleA = tpMath.asByteArray(input1);
		assertArrayEquals(String.valueOf(input1).getBytes(), bDoubleA);
	}

	@Test
	public final void testAsByteArrayLong() {
		long input1 = 342349878;
		byte[] res = tpMath.asByteArray(input1);
		assertArrayEquals(String.valueOf(input1).getBytes(), res);
	}

	@Test
	public final void testAsLong() {
		String str1 = "9878";
		Marker m1 = getMarker(str1);
		long res = tpMath.asLong(str1.getBytes(), m1);
		assertEquals(str1, String.valueOf(res));
	}

	@Test
	public final void testAsDouble() {
		String str1 = "98.78";
		Marker m1 = getMarker(str1);
		double res = tpMath.asDouble(str1.getBytes(), m1);
		assertEquals(str1, String.valueOf(res));
	}
}
