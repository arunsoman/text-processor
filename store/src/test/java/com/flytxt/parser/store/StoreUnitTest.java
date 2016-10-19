package com.flytxt.parser.store;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;
import com.flytxt.parser.marker.TokenFactory;

import io.mappedbus.MappedBusReader;

public class StoreUnitTest {

    public MarkerFactory getMf(final int size) {
        final MarkerFactory mf = new MarkerFactory();
        mf.setMaxListSize(size);
        return mf;
    }

    @Test
    public void test() {
        final String aon = "aon";
        final String age = "age";
        final Store store = new LocalFileStore("/tmp/out/", aon, age);
        store.set("my.csv");
        final String str = "10,twenty";
        final byte[] strB = str.getBytes();
        final MarkerFactory mf = getMf(str.split(",").length);
        final Marker line = mf.create(0, strB.length - 1);
        final List<Marker> ms = line.splitAndGetMarkers(strB, TokenFactory.create(","), mf);
        final Marker aonM = ms.get(1);
        final Marker ageM = ms.get(2);
        try {
            store.save(strB, "testFile", aonM, ageM);
            store.done();
        } catch (final IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void streamStoreTest() {
        final String aon = "aon";
        final String age = "age";
        final Store store = new StreamStore("/tmp/test", aon, age);
        store.set("my.csv");
        final String str = "10,twenty";
        final byte[] strB = str.getBytes();
        final MarkerFactory mf = getMf(str.split(",").length);
        final Marker line = mf.create(0, strB.length - 1);
        final List<Marker> ms = line.splitAndGetMarkers(strB, TokenFactory.create(","), mf);
        final Marker aonM = ms.get(1);
        final Marker ageM = ms.get(2);
        try {
            store.save(strB, "testFile", aonM, ageM);
            store.done();
            MappedBusReader reader = new MappedBusReader("/tmp/test", 100000L, 32);
            MarkerSerializer mserial = new MarkerSerializer();
            reader.open();
            if (reader.readType() == MarkerSerializer.TYPE)
                reader.readMessage(mserial);
            System.out.println(mserial);
            reader.close();
        } catch (final IOException e) {
            fail(e.getMessage());
        }
    }
}
