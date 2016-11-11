package com.flytxt.tp.translator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.flytxt.tp.marker.ConstantMarker;
import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.translator.TpString;

import ch.qos.logback.classic.pattern.MarkerConverter;

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

    @Test
    public void testMergeImmutable() {
        final String str1 = "first";
        final String str2 = " second";
        final Marker mocker1 = getMarker(str1);
        final Marker mocker2 = getMarker(str2);
        final Marker result = tpString.merge(mocker1, mocker2, markerFactory);
        System.out.println(mocker1);
        System.out.println(mocker2);
        System.out.println(result);
        assertEquals(str1.concat(str2), result.toString());
    }

    @Test
    public void testMergeMmutable() {
        final String str1 = "first, second";
        Marker line = getMarker(str1);

        final Marker mocker1 = ConstantMarker.mnull;
        final Marker mocker2 = ConstantMarker.mnull;
        
        line.splitAndGetMarkers(",".getBytes(), new int []{1,2}, markerFactory, mocker1,mocker2);
        System.out.println(mocker1);
        System.out.println(mocker2);
        
        final Marker result = tpString.merge(mocker1, mocker2, markerFactory);
        System.out.println(mocker1);
        System.out.println(mocker2);
        System.out.println(result);

    }

}