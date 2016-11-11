package com.flytxt.parser.store;

import java.io.IOException;

import org.junit.Assert;

import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.marker.MarkerFactory;
import com.flytxt.tp.marker.MarkerSerDer;
import com.flytxt.tp.store.StreamStore;

public class StreamTest {

    public void test() throws IOException {
        MarkerFactory markerFactory = new MarkerFactory();
        StreamStore ss = new StreamStore("/tmp");
        String line = "";
        String[] split = line.split("|");
        Marker[] markers = new Marker[split.length];
        int index = 0;
        for (String l : split) {
            byte[] bytes = l.getBytes();
            markers[index++] = markerFactory.createMarker(bytes, 0, bytes.length);
        }
        ss.save(null, "", markers);
        Assert.assertTrue(true);
    }
}