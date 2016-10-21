package com.flytxt.parser.store;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;
import com.github.arunsoman.ipc.mappedbus.MappedBusMessage;
import com.github.arunsoman.ipc.mappedbus.MemoryMappedFile;

public class MarkerSerializer implements MappedBusMessage {

    public static final int TYPE = 0;

    private Marker[] markers;

    private byte[] data;

    private MarkerFactory mf = new MarkerFactory();

    public MarkerSerializer() {
        mf.setMaxListSize(20);
    }

    public void set(byte[] data, Marker... markers) {
        this.data = data;
        this.markers = markers;
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
            this.markers[i++] = mf.create(0, 0);
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
            sb.append(" \"").append(marker.toString(data)).append('"').append(",");
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" }");
        return sb.toString();
    }
}
