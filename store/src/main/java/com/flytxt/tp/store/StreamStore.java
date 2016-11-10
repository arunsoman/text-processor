package com.flytxt.tp.store;

import java.io.IOException;
//
//import com.flytxt.commons.chronicle.ChronicleWriter;
//import com.flytxt.tp.marker.Marker;
//import com.flytxt.realtime.commons.RTEvent;

import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.marker.MarkerSerDer;

public class StreamStore implements Store {
	private MarkerSerDer serDer = new MarkerSerDer();
    @Override
    public void set(String fileName) {
        // TODO Auto-generated method stub

    }

    @Override
    public void save(byte[] data, String fileName, Marker... markers) throws IOException {
        byte[] serData = serDer.toBytes(markers);

    }

    @Override
    public String done() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }
 }
