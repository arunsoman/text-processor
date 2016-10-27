package com.flytxt.parser.lookup;

import static org.junit.Assert.assertEquals;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;

public class MatchTest {

    // @Test
    public void basicTest() {
        final String[][] data = { { "HelloWorld", "World" }, { "Wallnut", "fruit" }, { "HelloSmallWorld", "SmallWorld" } };
        final MarkerFactory mf = new MarkerFactory();
        mf.setMaxListSize(100);
        final MatchKey<Marker> object = new MatchKey<>(mf);
        for (final String[] datum : data)
            object.load(datum[0].getBytes(), mf.createMarker(datum[1].getBytes(), 0, datum[1].getBytes().length));

        Marker marker = object.get("Wallnut".getBytes());
        assertEquals("fruit", marker.getData());
    }
}
