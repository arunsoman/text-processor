package com.flytxt.tp.translator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.flytxt.tp.marker.ConstantMarker;
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
        final Marker mocker1 = markerFactory.createMarker("first");
        final Marker mocker2 = markerFactory.createMarker("second");;
        final Marker result = tpString.merge(mocker1, mocker2, markerFactory);
        assertEquals("first".concat("second"), result.toString());
    }

    @Test
    public void testMergeMmutable() {
        final String str1 = "first, second";
        Marker line = getMarker(str1);

        final Marker mocker1 = markerFactory.createMarker(null, 0, 0);
        Marker mocker2 = markerFactory.createMarker(null, 0, 0);;
        
        line.splitAndGetMarkers(",".getBytes(), new Router(new int []{0,1}), markerFactory, mocker1,mocker2);
        Marker mocker3 = tpString.lTrim(mocker2, markerFactory);
        final Marker result = tpString.merge(mocker1, mocker3, markerFactory);
        assertEquals("first".concat("second"), result.toString());
    }

}