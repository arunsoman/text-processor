package com.flytxt.parser.store;

import io.mappedbus.MappedBusMessage;
import io.mappedbus.MemoryMappedFile;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;

public class MarkerSerializer implements MappedBusMessage {

    public static final int TYPE = 0;

    private Marker[] markers;

    private byte[] data;

    private MarkerFactory mf;

    public void set(byte[] data, MarkerFactory mf, Marker... markers) {
        this.data = data;
        this.markers = markers;
        this.mf = mf;
    }

    @Override
    public void write(MemoryMappedFile mem, long pos) {
        mem.putInt(pos, this.markers.length);
        long dataSizePtr = (pos += 4); // int
        int dataSize = 0;
        pos += 4;// int
        for (Marker marker : markers) {
            mem.putInt(pos, marker.length);
            pos += 4; // int
            int len = marker.index + marker.length;
            dataSize += marker.length;
            for (int j = marker.index; j < len; j++)
                mem.putByte(pos++, data[j]);
        }
        mem.putInt(dataSizePtr, dataSize);
    }

    @Override
    public void read(MemoryMappedFile mem, long pos) {
        int dataPtr = 0;
        int i = 0;
        int markersLength = mem.getInt(pos);
        this.markers = new Marker[markersLength];
        while (i < markersLength)
            this.markers[i++] = mf.createMarker(null, 0, 0);
        int dataSize = mem.getInt(pos + 4);
        this.data = new byte[dataSize];
        pos += 8; // two int's
        for (i = 0; i < markersLength; i++) {
            int markerSize = mem.getInt(pos);
            pos += 4; // one int
            this.markers[i].index = dataPtr;
            this.markers[i].length = markerSize;
            for (int j = 0; j < markerSize; j++)
                data[dataPtr++] = mem.getByte(pos++);
        }
    }

    @Override
    public int type() {
        return TYPE;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (Marker marker : markers)
            sb.append(" \"").append(marker.toString()).append('"').append(",");
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" }");
        return sb.toString();
    }

    public RtEvent getEvent(RtEvent event) {
        if (event == null)
            event = new RtEvent();
        event.set(data, markers);
        return event;

    }

    private enum EventType {
        AGG_AND_TIME, AGG_ONLY, TIME_ONLY, DEFAULT_STATEFULL, STATELESS;
    };

    private enum FieldType {
        SECONDS, COUNT, AMOUNT, DATE, NAME
    };

    class RtEvent {

        private EventType eventType;

        private FieldType fieldType;

        private String eventName;

        private boolean statefull;

        private boolean aggregatable;

        private int eventTimeIndex;

        private int newValueIndex;

        private int oldValueIndex;

        private int eventId;

        private boolean passthrough;

        public void set(byte[] data, Marker... markers) {
            this.eventType = EventType.valueOf(markers[0].toString());
            this.fieldType = FieldType.valueOf(markers[1].toString());
            this.eventName = markers[2].toString();
            this.statefull = Boolean.valueOf(markers[3].toString());
            this.aggregatable = Boolean.valueOf(markers[4].toString());
            this.eventTimeIndex = Integer.parseInt(markers[5].toString());
            this.newValueIndex = Integer.parseInt(markers[6].toString());
            this.oldValueIndex = Integer.parseInt(markers[7].toString());
            this.eventId = Integer.parseInt(markers[8].toString());
            this.passthrough = Boolean.valueOf(markers[9].toString());
        }
    }
}
