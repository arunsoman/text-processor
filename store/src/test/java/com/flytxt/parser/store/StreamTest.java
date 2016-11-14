package com.flytxt.parser.store;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.marker.MarkerFactory;
import com.flytxt.tp.store.StreamStore;

public class StreamTest {

    @Test
    public void test() throws IOException {
        MarkerFactory markerFactory = new MarkerFactory();
        StreamStore ss = new StreamStore("/tmp");
        ss.set("StreamTest-test");
        String line = "1234567890,12,2,1,11,182745681129,,1478771400000,1478771458907,1478771458907,recharge";
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
    
    @Test @Ignore
    public void largeDataTest() throws IOException {
        MarkerFactory markerFactory = new MarkerFactory();
        StreamStore ss = new StreamStore("/tmp");
        ss.set("StreamTest-test");
        for(int i = 0; i < 100000; i++){
        String line = i+"7890,12,2,1,11,182745681129,,1478771400000,1478771458907,1478771458907,recharge";
        String[] split = line.split(",");
        Marker[] markers = new Marker[split.length];
        int index = 0;
        for (String l : split) {
            byte[] bytes = l.getBytes();
            markers[index++] = markerFactory.createMarker(bytes, 0, bytes.length);
        }
        ss.save(null, "", markers);
        }
        Assert.assertTrue(true);
    }
}