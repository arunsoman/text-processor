package com.flytxt.tp.lookup;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.marker.MarkerFactory;

@Ignore("for now")
public class MatchKeyTest {

    @Test
    public void basicTest() {
        final String[][] data = { { "HelloWorld", "World" }, { "Wallnut", "fruit" }, { "HelloSmallWorld", "SmallWorld" } };
        final MarkerFactory mf = new MarkerFactory();
        final MatchKey<Marker> object = new MatchKey<>(mf);
        for (final String[] datum : data)
            object.load(datum[0].getBytes(),
            		mf.createMarker(datum[1]));

        Marker marker = object.get("Wallnut".getBytes());
        assertEquals("fruit", marker.toString());
    }
}
