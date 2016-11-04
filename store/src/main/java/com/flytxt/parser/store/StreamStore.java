package com.flytxt.parser.store;

import java.io.IOException;
import java.sql.Date;

import com.flytxt.commons.chronicle.ChronicleWriter;
import com.flytxt.parser.marker.Marker;
import com.flytxt.realtime.commons.DataType;
import com.flytxt.realtime.commons.EventAggType;
import com.flytxt.realtime.commons.RTEvent;

public class StreamStore implements Store {

    private RTEvent event = new RTEvent();

    private ChronicleWriter<RTEvent> cWriter;

    @Override
    public void set(String fileName) {
        cWriter = new ChronicleWriter<>(fileName);
    }

    @Override
    public void save(byte[] data, String fileName, Marker... markers) throws IOException {
        populateValues(markers);
        cWriter.write(event);
    }

    private void populateValues(Marker[] markers) {
        DataType dataType = DataType.valueOf(markers[0].toString());
        event.setDataType(dataType);
        event.setEventAggType(EventAggType.valueOf(markers[1].toString()));
        event.setEventId(Integer.parseInt(markers[2].toString()));
        event.setEventTime(Date.valueOf(markers[3].toString()));
        event.setFileTime(Date.valueOf(markers[4].toString()).getTime());
        event.setKpiName(markers[5].toString());
        event.setLineTime(Date.valueOf(markers[6].toString()).getTime());
        switch (dataType) {
        case DOUBLE:
            event.setNewValue(Double.valueOf(markers[7].toString()));
            event.setOldValue(Double.valueOf(markers[8].toString()));
            break;
        case LONG:
            event.setNewValue(Long.valueOf(markers[7].toString()));
            event.setOldValue(Long.valueOf(markers[8].toString()));
            break;
        case STRING:
            event.setNewValue(markers[7].toString());
            event.setOldValue(markers[8].toString());
            break;
        default:
            break;
        }
        event.setSubscriberCircleId(Integer.parseInt(markers[9].toString()));
        event.setSubscriberId(Long.parseLong(markers[10].toString()));
    }

    @Override
    public String done() throws IOException {
        cWriter.destroy();
        return null;
    }

}
