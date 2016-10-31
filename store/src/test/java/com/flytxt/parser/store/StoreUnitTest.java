package com.flytxt.parser.store;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;

public class StoreUnitTest {

    private MarkerFactory mf;

    @Before
    public void getMf() {
        mf = new MarkerFactory();
    }

    @Test
    public void test() {
        final String aon = "aon";
        final String age = "age";
        final Store store = new LocalFileStore("/tmp/out/", aon, age);
        store.set("my.csv");
        final String str = "10,twenty";
        final byte[] strB = str.getBytes();
        mf.getCurrentObject().setCurrentLine(strB, 0, strB.length);
        final Marker line = mf.createMarker(null, 0, strB.length - 1);
        final Marker aonM = mf.createMarker(null, 0, strB.length - 1), ageM = mf.createMarker(null, 0, strB.length - 1);
        line.splitAndGetMarkers(",".getBytes(), new int[] { 1, 3 }, mf, aonM, ageM);
        try {
            store.save(strB, "testFile", aonM, ageM);
            store.done();
        } catch (final IOException e) {
            fail(e.getMessage());
        }
    }

    /*
     * @Test public void streamStoreTest() { final String aon = "aon"; final String age = "age"; final Store store = new StreamStore("/tmp/test", aon, age); store.set("my.csv"); final String str =
     * "10,twenty"; final byte[] strB = str.getBytes(); mf.getCurrentObject().setCurrentLine(strB, 0, strB.length); final Marker line = mf.createMarker(null, 0, strB.length - 1); final Marker aonM =
     * mf.createMarker(null, 0, strB.length - 1), ageM = mf.createMarker(null, 0, strB.length - 1); line.splitAndGetMarkers(",".getBytes(), new int[] { 1, 2 }, mf, aonM, ageM); try { store.save(strB,
     * "testFile", aonM, ageM); store.done(); MappedBusReader reader = new MappedBusReader("/tmp/test", 100000L, 32); MarkerSerializer mserial = new MarkerSerializer(); mserial.set(strB, mf, aonM);
     * reader.open(); if (reader.readType() == MarkerSerializer.TYPE) reader.readMessage(mserial); System.out.println(mserial); reader.close(); } catch (final IOException e) { fail(e.getMessage()); }
     * finally { File testFile = new File("/tmp/test"); testFile.delete();
     *
     * } }
     */
}
