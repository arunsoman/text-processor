package com.flytxt.parser.lookup;

import org.junit.Test;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;

public class MatchTest {

    final MarkerFactory mf = new MarkerFactory();

    @Test
    public void basicTest() {
        final String[][] data = { { "Hell", "World" }, { "Wall", "fruit" }, { "Pal", "SmallWorld" }, };
        final MatchKey<Marker> object = new MatchKey<>();
        mf.setMaxListSize(100);

        for (final String[] datum : data) {
            object.load(datum[0].getBytes(), mf.createImmutable(datum[1].getBytes(), 0, datum[1].getBytes().length));
        }
        Marker marker = object.get("lln".getBytes());
        System.out.println((marker == null) ? "null" : marker.toString(marker.getData()));

        marker = object.get("Hell".getBytes());
        System.out.println((marker == null) ? "null" : marker.toString(marker.getData()));
    }
}
