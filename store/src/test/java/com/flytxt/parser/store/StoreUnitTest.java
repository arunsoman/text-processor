package com.flytxt.parser.store;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;
import com.flytxt.parser.marker.TokenFactory;

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
        final Store store = new Store("/tmp/out/my.csv", aon, age);
        final String str = "10,twenty";
        final byte[] strB = str.getBytes();
        final MarkerFactory mf = getMf(str.split(",").length);
        final Marker line = mf.create(0, strB.length - 1);
        final List<Marker> ms = line.splitAndGetMarkers(strB, TokenFactory.create(","), mf);
        final Marker aonM = ms.get(1);
        final Marker ageM = ms.get(2);
        try {
            store.save(strB, aonM, ageM);
            store.done();
        } catch (final IOException e) {
            fail(e.getMessage());
        }

    }

}
