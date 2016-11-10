package com.flytxt.tp.lookup;

import static org.junit.Assert.assertEquals;

import com.flytxt.tp.lookup.MatchKey;
import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.marker.MarkerFactory;

public class MatchTest {

    // @Test
    public void basicTest() {
        final String[][] data = { { "HelloWorld", "World" }, { "Wallnut", "fruit" }, { "HelloSmallWorld", "SmallWorld" } };
        final MarkerFactory mf = new MarkerFactory();
        final MatchKey<Marker> object = new MatchKey<>(mf);
        for (final String[] datum : data)
            object.load(datum[0].getBytes(), mf.createMarker(datum[1].getBytes(), 0, datum[1].getBytes().length));

        Marker marker = object.get("Wallnut".getBytes());
        assertEquals("fruit", marker.getData());
    }
}
