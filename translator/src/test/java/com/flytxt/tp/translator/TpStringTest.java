package com.flytxt.tp.translator;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.marker.Router;

public class TpStringTest extends TpAbsTest {

	TpString tpString = new TpString();

	@Test
	public void testToUpperCase() {
		final String str = "abc";
		final Marker mocker = getMarker(str);
		final Marker result = tpString.toUpperCase(mocker, markerFactory);
		assertEquals(str.toUpperCase(), result.toString());
	}

	@Test
	public void testToLowerCase() {
		final String str = "ABC";
		final Marker mocker = getMarker(str);
		final Marker result = tpString.toLowerCase(mocker, markerFactory);
		assertEquals(str.toLowerCase(), result.toString());
	}

	/*
	 * @Test public void testToTitleCase() { final String str = "abc"; final
	 * Marker mocker = getMarker(str); final Marker result =
	 * tpString.toTitleCase(mocker, markerFactory); assertEquals("Abc",
	 * result.toString()); }
	 */

	@Test
	public void testLTrim() {
		final String str = "  abc ";
		final Marker mocker = getMarker(str);
		final Marker result = tpString.lTrim(mocker, markerFactory);
		assertEquals("abc ", result.toString());
	}

	@Test
	public void testRTrim() {
		final String str = "  abc ";
		final Marker mocker = getMarker(str);
		final Marker result = tpString.rTrim(mocker, markerFactory);
		assertEquals("  abc", result.toString());
	}

	@Test
	public void testLTrimWithAllSpaces() {
		final String str = "   ";
		final Marker mocker = getMarker(str);
		final Marker result = tpString.lTrim(mocker, markerFactory);
		assertEquals(0, result.length);
	}

	@Test
	public void testRTrimWithAllSpaces() {
		final String str = "    ";
		final Marker mocker = getMarker(str);
		final Marker result = tpString.rTrim(mocker, markerFactory);
		assertEquals(0, result.length);
	}

	@Test
	public void testTrimWithAllSpacesEven() {
		final String str = "    ";
		final Marker mocker = getMarker(str);
		final Marker result = tpString.trim(mocker, markerFactory);
		assertEquals(0, result.length);
	}

	@Test
	public void testTrimWithAllSpacesOdd() {
		final String str = "     ";
		final Marker mocker = getMarker(str);
		final Marker result = tpString.trim(mocker, markerFactory);
		assertEquals(0, result.length);
	}

	@Test
	public void testTrimWithSpacesEven() {
		final String str = "  r  ";
		final Marker mocker = getMarker(str);
		final Marker result = tpString.trim(mocker, markerFactory);
		assertEquals(str.trim(), result.toString());
	}

	@Test
	public void testTrimWithSpacesOdd() {
		final String str = "    r ";
		final Marker mocker = getMarker(str);
		final Marker result = tpString.trim(mocker, markerFactory);
		assertEquals(str.trim(), result.toString());
	}

	@Test
	public void testMergeImmutable() {
		final Marker mocker1 = markerFactory.createMarker("first");
		final Marker mocker2 = markerFactory.createMarker("second");
		;
		final Marker result = tpString.merge(mocker1, mocker2, markerFactory);
		assertEquals("first".concat("second"), result.toString());
	}

	@Test
	public void testMergeMmutable() {
		final String str1 = "first, second";
		Marker line = getMarker(str1);

		final Marker mocker1 = markerFactory.createMarker(null, 0, 0);
		Marker mocker2 = markerFactory.createMarker(null, 0, 0);
		;

		line.splitAndGetMarkers(",".getBytes(), new Router(new int[] { 0, 1 }), markerFactory, mocker1, mocker2);
		Marker mocker3 = tpString.lTrim(mocker2, markerFactory);
		final Marker result = tpString.merge(mocker1, mocker3, markerFactory);
		assertEquals("first".concat("second"), result.toString());
	}

	@Test
	public void testLength() {
		final String str = "123#90";
		final Marker mocker = getMarker(str);
		final Marker result = tpString.length(mocker, markerFactory);
		assertEquals(str.length(), result.asInt());
	}

	@Test
	public void testIsNull() {
		final String str1 = "123#90";
		final String str3 = "";
		final Marker mocker1 = getMarker(str1);
		final boolean result1 = tpString.isNull(mocker1);

		final Marker mocker2 = null;
		final boolean result2 = tpString.isNull(mocker2);

		final Marker mocker3 = getMarker(str3);
		final boolean result3 = tpString.isNull(mocker3);

		assertEquals(str1.equals(null), result1);
		assertEquals(str1 != null, result2);
		assertEquals(str3.isEmpty(), result3);
	}

	@Test
	public void testContains() {
		final Marker mocker1 = markerFactory.createMarker("malayalam");
		final Marker mocker2 = markerFactory.createMarker("laya");
		final Marker mocker3 = markerFactory.createMarker("raga");
		final boolean result1 = tpString.contains(mocker1, mocker2);
		final boolean result2 = tpString.contains(mocker1, mocker3);
		assertEquals("malayalam".contains("laya"), result1);
		assertEquals("malayalam".contains("raga"), result2);
	}

	@Test
	public void testContainsIgnoreCase() {
		final Marker mocker1 = markerFactory.createMarker("malayalam");
		final Marker mocker2 = markerFactory.createMarker("Laya");
		final Marker mocker3 = markerFactory.createMarker("laga");
		final boolean result1 = tpString.containsIgnoreCase(mocker1, mocker2);
		final boolean result2 = tpString.containsIgnoreCase(mocker1, mocker3);
		assertEquals("malayalam".toLowerCase().contains("Laya".toLowerCase()), result1);
		assertEquals("malayalam".toLowerCase().contains("laga".toLowerCase()), result2);
	}

	@Test
	public void testExtractLeading() {
		final String str = "GUJARAT";
		final Marker mocker = getMarker(str);
		final Marker result = tpString.extractLeading(mocker, 3, markerFactory);
		assertEquals(str.substring(0, 3), result.toString());
	}

	@Test
	public void testExtractTrailing() {
		final Marker mocker1 = markerFactory.createMarker("GUJARAT");
		final Marker mocker2 = markerFactory.createMarker("");

		final Marker result1 = tpString.extractTrailing(mocker1, 3, markerFactory);
		assertEquals("GUJARAT".substring(4, "GUJARAT".length()), result1.toString());

		try {
			final Marker result2 = tpString.extractTrailing(null, 3, markerFactory);
			assertEquals("", result2.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			final Marker result3 = tpString.extractTrailing(mocker2, -1, markerFactory);
			Assert.fail();
		} catch (Exception e) {
		}
	}

	@Test
	public void testEndsWith() {
		final Marker mocker1 = markerFactory.createMarker("GUJARAT");
		final Marker mocker2 = markerFactory.createMarker("RAT");
		final Marker mocker3 = markerFactory.createMarker("MAT");

		final boolean result1 = tpString.endsWith(mocker1, mocker2);
		assertEquals("GUJARAT".endsWith("RAT"), result1);

		final boolean result2 = tpString.endsWith(mocker1, mocker3);
		assertEquals("GUJARAT".endsWith("MAT"), result2);
	}

	@Test
	public void testEndsWithIgnore() {

		final Marker mocker1 = markerFactory.createMarker("GUJARAT");
		final Marker mocker2 = markerFactory.createMarker("RAT");
		final Marker mocker3 = markerFactory.createMarker("rAT");
		final Marker mocker4 = markerFactory.createMarker("MAT");

		final boolean result1 = tpString.endsWithIgnore(mocker1, mocker2);
		assertEquals("GUJARAT".toLowerCase().endsWith("RAT".toLowerCase()), result1);

		final boolean result2 = tpString.endsWithIgnore(mocker1, mocker3);
		assertEquals("GUJARAT".toLowerCase().endsWith("rAT".toLowerCase()), result2);

		final boolean result3 = tpString.endsWithIgnore(mocker1, mocker4);
		assertEquals("GUJARAT".toLowerCase().endsWith("MAT".toLowerCase()), result3);
	}

	@Test
	public void testStartsWith() {
		final String str1 = "GUJARAT";
		final Marker mocker1 = getMarker(str1);
		final String str2 = "GUJ";
		final Marker mocker2 = getMarker(str2);
		final boolean result = tpString.startsWith(mocker1, mocker2);
		assertEquals("GUJARAT".startsWith("GUJ"), result);
	}

	@Test
	public void testEquals() {
		final String str1 = "GUJARAT";
		final Marker mocker1 = getMarker(str1);
		final String str2 = "GUJARAT";
		final Marker mocker2 = getMarker(str2);
		final boolean result = tpString.equals(mocker1, mocker2);
		assertEquals("GUJARAT".equals("GUJARAT"), result);
	}

	@Test
	public void testToTitleCase() {

		final Marker mocker1 = markerFactory.createMarker("abc");
		final Marker mocker2 = markerFactory.createMarker("");
		final Marker mocker3 = markerFactory.createMarker("{BC");
		final Marker mocker4 = markerFactory.createMarker("Bc");
		final Marker mocker5 = markerFactory.createMarker("aBc");
		final Marker mocker6 = markerFactory.createMarker("ABC");

		final Marker result1 = tpString.toTitleCase(mocker1, markerFactory);
		assertEquals("Abc", result1.toString());

		final Marker result2 = tpString.toTitleCase(mocker2, markerFactory);
		assertEquals("", result2.toString());

		final Marker result3 = tpString.toTitleCase(mocker3, markerFactory);
		assertEquals("{bc", result3.toString());

		final Marker result4 = tpString.toTitleCase(mocker4, markerFactory);
		assertEquals("Bc", result4.toString());

		final Marker result5 = tpString.toTitleCase(mocker5, markerFactory);
		assertEquals("Abc", result5.toString());

		final Marker result6 = tpString.toTitleCase(mocker6, markerFactory);
		assertEquals("Abc", result6.toString());

	}

	@Test
	public void testTrim() {
		final String str = "abc";
		final Marker mocker = getMarker(str);
		final Marker result = tpString.trim(mocker, markerFactory);
		assertEquals("abc", result.toString());
	}

	@Test
	public void testTrimRight() {
		final String str = "abc ";
		final Marker mocker = getMarker(str);
		final Marker result = tpString.trim(mocker, markerFactory);
		assertEquals("abc", result.toString());
	}

	@Test
	public void testTrimLeft() {
		final String str = " abc";
		final Marker mocker = getMarker(str);
		final Marker result = tpString.trim(mocker, markerFactory);
		assertEquals("abc", result.toString());
	}

}