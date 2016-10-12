package com.flytxt.parser.lookup;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;

public class SearchLookupTest {

    final MarkerFactory mf = new MarkerFactory();

    @Test
    public void basicTest() {
        final String[][] data = { { "Hello World", "World" }, { "Wallnut", "fruit" } };
        mf.setMaxListSize(100);
        final Search<Marker> object = new Search<>(mf);
        for (final String[] datum : data)
            object.load(datum[0].getBytes(), mf.createImmutable(datum[1].getBytes(), 0, datum[1].getBytes().length));
        final Marker marker = object.get("lln".getBytes());
        assertEquals("fruit", marker.toString());
    }

    @Test
    public void biggerSearch() {
        final String[][] data = { { "98744", "test" }, { "99478", "fruit" }, { "99477", "World" } };
        final Search<Marker> search = new Search<>(mf);
        for (final String[] datum : data)
            search.load(datum[0].getBytes(), mf.createImmutable(datum[1].getBytes(), 0, datum[1].getBytes().length));
        final Marker marker = search.get("9947746480".getBytes());
        assertEquals("World", marker.toString());
    }
}
