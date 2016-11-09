package com.flytxt.parser.store;

import java.io.IOException;
//
//import com.flytxt.commons.chronicle.ChronicleWriter;
//import com.flytxt.parser.marker.Marker;
//import com.flytxt.realtime.commons.RTEvent;

import com.flytxt.parser.marker.Marker;

public class StreamStore implements Store {

    @Override
    public void set(String fileName) {
        // TODO Auto-generated method stub

    }

    @Override
    public void save(byte[] data, String fileName, Marker... markers) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public String done() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }
    //
    // private RTEvent event = new RTEvent();
    //
    // private ChronicleWriter<RTEvent> cWriter;
    //
    // @Override
    // public void set(String fileName) {
    // cWriter = new ChronicleWriter<>(fileName);
    // }
    //
    // @Override
    // public void save(byte[] data, String fileName, Marker... markers) throws IOException {
    // populateValues(markers);
    // cWriter.write(event);
    // }
    //
    // private void populateValues(Marker[] markers) {
    // event.setDataType(markers[0]);
    // event.setEventAggType(markers[1]);
    // event.setEventId(markers[2]);
    // event.setEventTime(markers[3]);
    // event.setFileTime(markers[4]);
    // event.setKpiName(markers[5]);
    // event.setLineTime(markers[6]);
    //
    // event.setNewValue(markers[7]);
    // event.setOldValue(markers[8]);
    //
    // event.setSubscriberCircleId(markers[9]);
    // event.setSubscriberId(markers[10]);
    // }
    //
    // @Override
    // public String done() throws IOException {
    // cWriter.destroy();
    // return null;
    // }

}
