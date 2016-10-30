package com.flytxt.parser.marker;

public class ImmutableMarker extends Marker {

    private byte[] data;

    public ImmutableMarker(byte[] currentLine){
    	this.data = currentLine;
        this.index = 0;
        this.length = data.length;
    }
    
    @Override
    public void splitAndGetMarkers( final byte[] token, final int[] indexOfMarker, final MarkerFactory mf, Marker... markers) {
    	find(data, token, indexOfMarker, mf, markers);
    }

    @Override
    public byte[] getData() {
        return this.data;
    }

    @Override
    public String toString() {
        return new String(this.data, this.index, this.length);
    }
}
