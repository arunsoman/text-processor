package com.flytxt.parser.translator;

import static org.junit.Assert.assertEquals;

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

    private Marker getMarer(final String str) {
        final Marker mocker = new Marker();
        mocker.index = 0;
        mocker.length = str.length();
        return mocker;
    }

    @Test
    public void testToUpperCase() {
        final String str = "abc";
        final byte[] data = str.getBytes();
        final Marker mocker = getMarer(str);
        final Marker result = tpString.toUpperCase(data, mocker, mf);
        assertEquals(str.toUpperCase(), result.toString(result.getData()));
    }

    @Test
    public void testToLowerCase() {
        final String str = "ABC";
        final byte[] data = str.getBytes();
        final Marker mocker = getMarer(str);
        final Marker result = tpString.toLowerCase(data, mocker, mf);
        assertEquals(str.toLowerCase(), result.toString(result.getData()));
    }

    @Test
    public void testToTitleCase() {
        final String str = "abc";
        final byte[] data = str.getBytes();
        final Marker mocker = getMarer(str);
        final Marker result = tpString.toTitleCase(data, mocker, mf);
        assertEquals("Abc", result.toString(result.getData()));
    }

    @Test
    public void testLTrim() {
        final String str = "  abc ";
        final byte[] data = str.getBytes();
        final Marker mocker = getMarer(str);
        final Marker result = tpString.lTrim(data, mocker, mf);
        assertEquals("abc ", result.toString(data));
    }

    @Test
    public void testRTrim() {
        final String str = "  abc ";
        final byte[] data = str.getBytes();
        final Marker mocker = getMarer(str);
        final Marker result = tpString.rTrim(data, mocker, mf);
        assertEquals("  abc", result.toString(data));
    }

    @Test
    public void testLTrimWithAllSpaces() {
        final String str = "   ";
        final byte[] data = str.getBytes();
        final Marker mocker = getMarer(str);
        final Marker result = tpString.lTrim(data, mocker, mf);
        assertEquals(0, result.length);
    }

    @Test
    public void testRTrimWithAllSpaces() {
        final String str = "    ";
        final byte[] data = str.getBytes();
        final Marker mocker = getMarer(str);
        final Marker result = tpString.rTrim(data, mocker, mf);
        assertEquals(0, result.length);
    }

    @Test
    public void testTrimWithAllSpacesEven() {
        final String str = "    ";
        final byte[] data = str.getBytes();
        final Marker mocker = getMarer(str);
        final Marker result = tpString.trim(data, mocker, mf);
        assertEquals(0, result.length);
    }

    @Test
    public void testTrimWithAllSpacesOdd() {
        final String str = "     ";
        final byte[] data = str.getBytes();
        final Marker mocker = getMarer(str);
        final Marker result = tpString.trim(data, mocker, mf);
        assertEquals(0, result.length);
    }

    @Test
    public void testTrimWithSpacesEven() {
        final String str = "  r  ";
        final byte[] data = str.getBytes();
        final Marker mocker = getMarer(str);
        final Marker result = tpString.trim(data, mocker, mf);
        assertEquals(str.trim(), result.toString(data));
    }

    @Test
    public void testTrimWithSpacesOdd() {
        final String str = "    r ";
        final byte[] data = str.getBytes();
        final Marker mocker = getMarer(str);
        final Marker result = tpString.trim(data, mocker, mf);
        assertEquals(str.trim(), result.toString(data));
    }

}