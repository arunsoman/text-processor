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
        int count = 1, lastIndex = this.index, currentIndex = this.index, tokenIndex, index = 0;
        while (currentIndex < this.index + length) {
            for (tokenIndex = 0; tokenIndex < token.length && token[tokenIndex] == data[currentIndex + tokenIndex]; tokenIndex++)
                ;
            if (tokenIndex == token.length) { // true if token found at currentIndex
                if (indexOfMarker[index] == count++) { // true if correct marker is found
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

    @Override
    public byte[] getData() {
        return this.data;
    }

    @Override
    public String toString() {
        return new String(this.data, this.index, this.length);
    }
}
