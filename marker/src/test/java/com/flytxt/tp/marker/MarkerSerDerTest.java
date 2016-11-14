package com.flytxt.tp.marker;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.marker.MarkerFactory;
import com.flytxt.tp.marker.MarkerSerDer;

public class MarkerSerDerTest {

    @Test
    public void test() throws IOException {
        MarkerFactory markerFactory = new MarkerFactory();
        String line = "1234567890,12,2,1,11,182745681129, ,1478771400000,1478771458907,1478771458907,recharge";
        String[] tokens = line.split(",");
        Marker[] markers = new Marker[tokens.length];
        int index = 0;
        for (String token : tokens) {
            byte[] bytes = token.getBytes();
            markers[index++] = markerFactory.createMarker(bytes, 0, bytes.length);
        }

        MarkerSerDer msd = new MarkerSerDer();
        byte[] bytes = msd.toBytes(markers);
        Marker[] newMarkers = msd.fromBytes(bytes);

        for (int i = 0; i < newMarkers.length; i++) {
            Assert.assertEquals(markers[i].toString(), newMarkers[i].toString());
        }
    }
}