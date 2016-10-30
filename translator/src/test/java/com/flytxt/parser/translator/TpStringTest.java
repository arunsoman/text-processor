package com.flytxt.parser.translator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerDefaultConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MarkerDefaultConfig.class, TranslatorDefaultConfig.class})
public class TpStringTest extends TpAbsTest {

	@Autowired
	TpString tpString;

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

	@Test
	public void testToTitleCase() {
		final String str = "abc";
		final Marker mocker = getMarker(str);
		final Marker result = tpString.toTitleCase(mocker, markerFactory);
		assertEquals("Abc", result.toString());
	}

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

}