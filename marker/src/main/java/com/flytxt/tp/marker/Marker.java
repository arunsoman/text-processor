package com.flytxt.tp.marker;

public class Marker implements Comparable<byte[]> {

    public int index;

    public int length;

    private CurrentObject currentObject;

    Marker() {
    }

    public Marker(CurrentObject currentObject) {
        this.currentObject = currentObject;
    }

    public void setLineAttribute(int index, int length) {
        this.index = index;
        this.length = length;
    }

    public void splitAndGetMarkers(final byte[] token, final int[] indexOfMarker, final MarkerFactory mf, Marker... markers) {
        byte[] data = currentObject.getLine();
        find(data, token, indexOfMarker, mf, markers);
    }

    protected void find(byte[] data, byte[] token, int[] indexOfMarker, MarkerFactory mf, Marker... markers) {
        int count = 1, lastIndex = this.index, currentIndex = this.index, tokenIndex, index = 0;
        Router router = mf.findRouter(indexOfMarker);
        while (currentIndex < this.index + length) {
            for (tokenIndex = 0; tokenIndex < token.length && token[tokenIndex] == data[currentIndex + tokenIndex]; tokenIndex++)
                ;
            if (tokenIndex == token.length) { // true if token found at currentIndex
                if (router.getMarkerPosition(index) == count++) { // true if current marker is to be stored
                    markers[index].index = lastIndex;
                    markers[index].length = currentIndex - lastIndex;
                    index++;
                }
                if (index == indexOfMarker.length)
                    return;
                currentIndex = currentIndex + token.length;
                lastIndex = currentIndex;
            } else
                currentIndex++;
        }
        if (lastIndex > this.index && indexOfMarker[index] == count) {
            markers[index].index = lastIndex;
            markers[index].length = this.length - lastIndex;
        }
    }

    public byte[] getData() {
        return currentObject.getLine();
    }

    @Override
    public String toString() {
        return new String(currentObject.getLine(), index, length);
    }

    @Override
    public int compareTo(byte[] o) {
        // TODO Auto-generated method stub
        return 0;
    }

    public int asInt() {
        if (length == 0) {
            return 0;
        }
        return Integer.parseInt(toString());
    }

    public long asLong() {
        if (length == 0) {
            return 0;
        }
        return Long.parseLong(toString());
    }

    public double asDouble() {
        if (length == 0) {
            return 0;
        }
        return Double.parseDouble(toString());
    }
}
