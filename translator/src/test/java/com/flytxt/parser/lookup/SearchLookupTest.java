package com.flytxt.parser.lookup;

import org.junit.Test;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;

public class SearchLookupTest {

    @Test
    public void basicTest() {
        final String[][] data = { { "Hello World", "World" }, { "Wallnut", "fruit" } };
        final Search<Marker> object = new Search<>();
        final MarkerFactory mf = new MarkerFactory();
        mf.setMaxListSize(100);

        for (final String[] datum : data) {
            object.load(datum[0].getBytes(), mf.createImmutable(datum[1].getBytes(), 0, datum[1].getBytes().length));
        }
        final Marker marker = object.get("lln".getBytes());
        System.out.println(marker.toString(marker.getData()));
    }
}
