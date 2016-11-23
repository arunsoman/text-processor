package com.flytxt.tp.marker;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MarkerSerDer {

    public Marker[] fromBytes(byte[] data) throws IOException {
        ByteArrayInputStream bIs = new ByteArrayInputStream(data);
        DataInputStream dIs = new DataInputStream(bIs);
        int size = dIs.readInt();
        Marker markers[] = new Marker[size];
        int index = 0;
        for (Marker aMarker : markers) {
            int dataSize = dIs.readInt();
            byte[] mData = new byte[dataSize];
            dIs.read(mData);
            Marker m = new Marker(mData, 0, dataSize);
            markers[index++] = m;
        }
        return markers;
    }

    public byte[] toBytes(Marker... markers) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dOs = new DataOutputStream(bos);
        toBytes(dOs, markers);
        byte[] data = bos.toByteArray();
        dOs.flush();
        bos.reset();
        return data;
    }

    private void toBytes(DataOutputStream dOs, Marker... markers) throws IOException {
        dOs.writeInt(markers.length);
        for (Marker aMarker : markers) {
            dOs.writeInt(aMarker.length);
            dOs.write(aMarker.getData(), aMarker.index, aMarker.length);
        }
        dOs.flush();
    }
}
