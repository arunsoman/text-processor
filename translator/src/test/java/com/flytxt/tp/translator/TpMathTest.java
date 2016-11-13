package com.flytxt.tp.translator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.translator.TpMath;


public class TpMathTest extends TpAbsTest {
	
	TpMath tpMath = new TpMath();

	@Test
	public final void testAbs() {
		String str = "-98.78";
		Marker m = getMarker(str);
		Marker actual = tpMath.abs(m, markerFactory);
		assertEquals(String.valueOf(Math.abs(Double.parseDouble(str))), actual.toString());
	}

	@Test
	public final void testLessThan() {
		String str1 = "-98.78";
		Marker m1 = getMarker(str1);
		String str2 = "-99.78";
		Marker m2 = getMarker(str2);
		assertEquals(Double.parseDouble(str1)<Double.parseDouble(str2),
				tpMath.lessThan( m1,  m2, markerFactory));
	}

	@Test
	public final void testLessEqThan() {
		String str1 = "-98.78";
		Marker m1 = getMarker(str1);
		String str2 = "-98.78";
		Marker m2 = getMarker(str2);
		assertEquals(Double.parseDouble(str1)<=Double.parseDouble(str2),
		             tpMath.lessEqThan( m1,  m2, markerFactory));
	}

	@Test
	public final void testGreaterEqThan() {
		String str1 = "99.78";
		Marker m1 = getMarker(str1);
		String str2 = "98.78";
		Marker m2 = getMarker(str2);
		assertEquals(Double.parseDouble(str1)>=Double.parseDouble(str2),
				tpMath.greaterEqThan( m1,  m2, markerFactory));
	}

	@Test
	public final void testGreaterThan() {
		String str1 = "98.78";
		Marker m1 = getMarker(str1);
		String str2 = "98.78";
		Marker m2 = getMarker(str2);
		assertEquals(tpMath.lessEqThan(m1,  m2, markerFactory), true);
	}

	@Test
	public final void testSubLong() {
		String str1 = "988";
		Marker m1 = markerFactory.createMarker(str1);
		String str2 = "9878";
		Marker m2 = markerFactory.createMarker(str2);
		Marker res = tpMath.subLong( m1,  m2, markerFactory);
		String str3 = String.valueOf(Long.parseLong(str1)-Long.parseLong(str2));
		assertEquals(str3, res.toString());
	}

	@Test
	public final void testSubDouble() {
		String str1 = "98.78";
		Marker m1 = getMarker(str1);
		String str2 = "98.78";
		Marker m2 = getMarker(str2);
		Marker res = tpMath.subDouble( m1,  m2, markerFactory);
		String str3 = String.valueOf(Double.parseDouble(str1)-Double.parseDouble(str2));
		assertEquals(str3, res.toString());
	}

	@Test
	public final void testAddLong() {
		String str1 = "9878";
		Marker m1 = getMarker(str1);
		String str2 = "9878";
		Marker m2 = getMarker(str2);
		Marker res = tpMath.addLong( m1,  m2, markerFactory);
		String str3 = String.valueOf(Long.parseLong(str1)+Long.parseLong(str2));
		assertEquals(str3, res.toString());
	}

	@Test
	public final void testAddDouble() {
		String str1 = "98.78";
		Marker m1 = getMarker(str1);
		String str2 = "98.78";
		Marker m2 = getMarker(str2);
		Marker res = tpMath.addDouble( m1,  m2, markerFactory);
		String str3 = String.valueOf(Double.parseDouble(str1)+Double.parseDouble(str2));
		assertEquals(str3, res.toString());
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
		Marker res = tpMath.ceil( m1,  markerFactory);
		String str3 = String.valueOf(Double.parseDouble(str1)+Double.parseDouble(str2));
		assertEquals(str3, res.toString());
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
		assertEquals(tpMath.eq( m1,  m2, markerFactory), true);
	}

	@Test
	public final void testExtractDecimalIntegerPart() {
		String str1 = "98.78";
		Marker m1 = getMarker(str1);
		Marker res = tpMath.extractDecimalIntegerPart( m1, markerFactory);
		assertEquals(str1.substring(0, str1.indexOf('.')), res.toString());
	}

	@Test
	public final void testExtractDecimalFractionPart() {
		String str1 = "98.78";
		String str2=".78";
		Marker m1 = getMarker(str1);
		Marker res = tpMath.extractDecimalFractionPart( m1, markerFactory);
		assertEquals(str2, res.toString());
	}

	@Test
	public final void testIsNumber() {
		String str1 = "98.78";
		Marker m1 = getMarker(str1);
		boolean res = tpMath.isNumber( m1, markerFactory);
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
		long res = tpMath.asLong( m1);
		assertEquals(str1, String.valueOf(res));
	}

	@Test
	public final void testAsDouble() {
		String str1 = "98.78";
		Marker m1 = getMarker(str1);
		double res = tpMath.asDouble( m1);
		assertEquals(str1, String.valueOf(res));
	}
}
