package com.flytxt.tp.marker;

public class ImmutableMarker extends Marker {

    private byte[] data;

    public ImmutableMarker(byte[] currentLine) {
        this.data = currentLine;
        this.index = 0;
        this.length = data.length;
    }

    public void setData(byte[] currentLine) {
        this.data = currentLine;
        this.index = 0;
        this.length = data.length;
    }
    @Override
    public void splitAndGetMarkers(final byte[] token, final int[] indexOfMarker, final MarkerFactory mf, Marker... markers) {
        find(true, data, token, indexOfMarker, mf, markers);
    }

    @Override
    public byte[] getData() {
        return this.data;
    }

    @Override
    public String toString() {
        try {
            return new String(this.data, this.index, this.length);
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }
}
