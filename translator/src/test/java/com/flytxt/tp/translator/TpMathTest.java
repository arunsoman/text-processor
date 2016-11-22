package com.flytxt.tp.translator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.apache.commons.math3.util.Precision;
import org.junit.Test;

import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.translator.TpMath;

public class TpMathTest extends TpAbsTest {

	TpMath tpMath = new TpMath();

	@Test
	public final void testAbs() {
		String str = "-98.78";
		Marker m = markerFactory.createMarker(str);
		Marker actual = tpMath.abs(m, markerFactory);
		assertEquals(String.valueOf(Math.abs(Double.parseDouble(str))), actual.toString());
	}

	@Test
	public final void testLessThan() {
		String str1 = "-98.78";
		Marker m1 = markerFactory.createMarker(str1);
		String str2 = "-99.78";
		Marker m2 = markerFactory.createMarker(str2);
		assertEquals(Double.parseDouble(str1) < Double.parseDouble(str2), tpMath.lessThan(m1, m2, markerFactory));
	}

	@Test
	public final void testLessEqThan() {
		String str1 = "-98.78";
		Marker m1 = markerFactory.createMarker(str1);
		String str2 = "-98.78";
		Marker m2 = markerFactory.createMarker(str2);
		assertEquals(Double.parseDouble(str1) <= Double.parseDouble(str2), tpMath.lessEqThan(m1, m2, markerFactory));
	}

	@Test
	public final void testGreaterEqThan() {
		String str1 = "99.78";
		Marker m1 = markerFactory.createMarker(str1);
		String str2 = "98.78";
		Marker m2 = markerFactory.createMarker(str2);
		assertEquals(Double.parseDouble(str1) >= Double.parseDouble(str2), tpMath.greaterEqThan(m1, m2, markerFactory));
	}

	@Test
	public final void testGreaterThan() {
		String str1 = "98.78";
		Marker m1 = markerFactory.createMarker(str1);
		String str2 = "98.78";
		Marker m2 = markerFactory.createMarker(str2);
		assertEquals(tpMath.lessEqThan(m1, m2, markerFactory), true);
	}

	@Test
	public final void testSubLong() {
		String str1 = "988";
		Marker m1 = markerFactory.createMarker(str1);
		String str2 = "9878";
		Marker m2 = markerFactory.createMarker(str2);
		Marker res = tpMath.subLong(m1, m2, markerFactory);
		String str3 = String.valueOf(Long.parseLong(str1) - Long.parseLong(str2));
		assertEquals(str3, res.toString());
	}

	@Test
	public final void testSubDouble() {
		String str1 = "98.78";
		Marker m1 = markerFactory.createMarker(str1);
		String str2 = "98.78";
		Marker m2 = markerFactory.createMarker(str2);
		Marker res = tpMath.subDouble(m1, m2, markerFactory);
		String str3 = String.valueOf(Double.parseDouble(str1) - Double.parseDouble(str2));
		assertEquals(str3, res.toString());
	}

	@Test
	public final void testAddLong() {
		String str1 = "9878";
		Marker m1 = markerFactory.createMarker(str1);
		String str2 = "878";
		Marker m2 = markerFactory.createMarker(str2);
		Marker res = tpMath.addLong(m1, m2, markerFactory);
		String str3 = String.valueOf(Long.parseLong(str1) + Long.parseLong(str2));
		assertEquals(str3, res.toString());
	}
	
	
	
	@Test
	public final void testMin() {
		String str1 = "98785";
		Marker m1 = markerFactory.createMarker(str1);
		String str2 = "878";
		Marker m2 = markerFactory.createMarker(str2);
		Marker res = tpMath.min(m1, m2, markerFactory);
		assertEquals(str2, res.toString());
	}
	@Test
	public final void testMax() {
		String str1 = "9870";
		Marker m1 = markerFactory.createMarker(str1);
		String str2 = "878";
		Marker m2 = markerFactory.createMarker(str2);
		Marker res = tpMath.max(m1, m2, markerFactory);
		assertEquals(str1, res.toString());
	}

	@Test
	public final void testAddDouble() {
		String str1 = "98.78";
		Marker m1 = markerFactory.createMarker(str1);
		String str2 = "98.78";
		Marker m2 = markerFactory.createMarker(str2);
		Marker res = tpMath.addDouble(m1, m2, markerFactory);
		String str3 = String.valueOf(Double.parseDouble(str1) + Double.parseDouble(str2));
		assertEquals(str3, res.toString());
	}

	@Test
	public final void testSub() {
	}

	@Test
	public final void testAdd() {
	}

	@Test
	public final void testCeil() {
		String str1 = "98.78";
		Marker m1 = markerFactory.createMarker(str1);
		Marker res = tpMath.ceil(m1, markerFactory);
		double str3 = Math.ceil(Double.parseDouble(str1));
		assertEquals(new Double(str3), Double.valueOf(res.toString()));

	}

	@Test
	public final void testFloor() {
		String str1 = "98.78";
		Marker m1 = markerFactory.createMarker(str1);
		Marker res = tpMath.floor(m1, markerFactory);
		double str3 = Math.floor(Double.parseDouble(str1));
		assertEquals(new Double(str3), Double.valueOf(res.toString()));
	}

	@Test
	public final void testRound() {
		String str1 = "98.78";
		Marker m1 = markerFactory.createMarker(str1);
		Marker res = tpMath.round(1, m1, markerFactory);
		String str3 = String.valueOf(Precision.round(Double.parseDouble(str1), 1, BigDecimal.ROUND_HALF_EVEN));
		assertEquals(str3, res.toString());
	}

	@Test
	public final void testEq() {
		String str1 = "98.78";
		Marker m1 = markerFactory.createMarker(str1);
		String str2 = "98.78";
		Marker m2 = markerFactory.createMarker(str2);
		assertEquals(tpMath.eq(m1, m2, markerFactory), true);
	}

	@Test
	public final void testExtractDecimalIntegerPart() {
		String str1 = "98.78";
		Marker m1 = markerFactory.createMarker(str1);
		Marker res = tpMath.extractDecimalIntegerPart(m1, markerFactory);
		assertEquals(str1.substring(0, str1.indexOf('.')), res.toString());
	}

	@Test
	public final void testExtractDecimalFractionPart() {
		String str1 = "98.78";
		String str2 = ".78";
		Marker m1 = markerFactory.createMarker(str1);
		Marker res = tpMath.extractDecimalFractionPart(m1, markerFactory);
		assertEquals(str2, res.toString());
	}

	@Test
	public final void testIsNumber() {
		String str1 = "98.78";
		Marker m1 = markerFactory.createMarker(str1);
		boolean res = tpMath.isNumber(m1, markerFactory);
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
		Marker m1 = markerFactory.createMarker(str1);
		long res = tpMath.asLong(m1);
		assertEquals(str1, String.valueOf(res));
	}

	@Test
	public final void testAsDouble() {
		String str1 = "98.78";
		Marker m1 = markerFactory.createMarker(str1);
		double res = tpMath.asDouble(m1);
		assertEquals(str1, String.valueOf(res));
	}
	
	
	// Added from here on-words 
	
	
	@Test
	public final void testMulDouble(){
		
		String str1 = "2.2";
		Marker m1 = markerFactory.createMarker(str1);
		String str2 = "2.2";
		String result = "4.84";
		Marker m2 = markerFactory.createMarker(str2);
		Marker res = tpMath.mulDouble(m1, m2, markerFactory);
		assertEquals(true, res.toString().contains(result));
		
	}
	
	@Test
	public final void testDivDouble(){
		
		String str1 = "2.2";
		Marker m1 = markerFactory.createMarker(str1);
		String str2 = "2.2";
		String result = "1.0";
		Marker m2 = markerFactory.createMarker(str2);
		Marker res = tpMath.divDouble(m1, m2, markerFactory);
		assertEquals(result, res.toString());
		
	}
	
	@Test
	public final void testToMarke_doubler(){		
		double doubleValue = 2.2;		
		Marker res = tpMath.toMarker(doubleValue,markerFactory);
		assertEquals(String.valueOf(doubleValue), res.toString());		
	}

	@Test
	public final void testToMarker_long(){		
		double longValue = 2;		
		Marker res = tpMath.toMarker(longValue,markerFactory);
		assertEquals(String.valueOf(longValue), res.toString());		
	}
			
	/**
	 * Test two markers are unequal even if the length exceeds 
	 */
	@Test
	public final void testEq_LengthExceed() {
		String str1 = "98.780";
		Marker m1 = markerFactory.createMarker(str1);
		String str2 = "98.78";
		Marker m2 = markerFactory.createMarker(str2);
		assertEquals(tpMath.eq(m1, m2, markerFactory), false);
	}
	

	/**
	 * Test the negative scenario, unequal Marker scenario  
	 */
	@Test
	public final void testEq_unequalPrecision() {
		String str1 = "98.780";
		Marker m1 = markerFactory.createMarker(str1);
		String str2 = "98.871";
		Marker m2 = markerFactory.createMarker(str2);
		assertEquals(tpMath.eq(m1, m2, markerFactory), false);
	}
	
	/**
	 * Test the negative scenario, unequal Marker scenario  
	 */
	@Test
	public final void testEq_unequalScale() {
		String str1 = "198.780";
		Marker m1 = markerFactory.createMarker(str1);
		String str2 = "298.780";
		Marker m2 = markerFactory.createMarker(str2);
		assertEquals(tpMath.eq(m1, m2, markerFactory), false);
	}
	
	
	@Test
	public final void testGraterThan() {
		String str1 = "98.78";
		Marker m1 = markerFactory.createMarker(str1);
		String str2 = "99.78";
		Marker m2 = markerFactory.createMarker(str2);
		assertEquals( false,tpMath.greaterThan(m1, m2, markerFactory));
	}	
}
