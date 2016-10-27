package com.flytxt.parser.marker;

public class ImmutableMarker extends Marker {

    private byte[] data;

    public ImmutableMarker() {
    }
    public void setData(byte[] currentLine){
    	this.data = currentLine;
        this.index = 0;
        this.length = data.length;
    }

    @Override
    public void splitAndGetMarkers(final byte[] dataNull, final byte[] token, final int[] indexOfMarker, final MarkerFactory mf, Marker... markers) {
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
    public Marker splitAndGetMarker(final byte[] dataNull, final byte[] token, final int indexOfMarker, final MarkerFactory mf) {

        int count = 1, lastIndex = index, currentIndex = index, tokenIndex;
        while (currentIndex - index <= length) {
            for (tokenIndex = 0; tokenIndex < token.length && token[tokenIndex] == data[currentIndex + tokenIndex]; tokenIndex++)
                ;
            if (tokenIndex == token.length) { // true if token found at currentIndex
                if (indexOfMarker == count++)
                    return mf.createImmutable(data, lastIndex, currentIndex - lastIndex);
                currentIndex = currentIndex + token.length;
                lastIndex = currentIndex;
            } else
                currentIndex++;
        }

        if (lastIndex < length + 1 && indexOfMarker == count)
            return mf.createImmutable(data, lastIndex, this.length - (lastIndex - 1));
        return null;
    }

    @Override
    public FlyList<Marker> splitAndGetMarkers(final byte[] dataNull, final byte[] token, final MarkerFactory mf) {

        final FlyList<Marker> markers = mf.getArrayList();
        int currentIndex = index, lastIndex = index, tokenIndex;
        boolean endReached = false;
        while (currentIndex - index <= length) {
            for (tokenIndex = 0; tokenIndex < token.length && token[tokenIndex] == data[currentIndex + tokenIndex]; tokenIndex++)
                ;
            if (tokenIndex == token.length) {
                if (markers.add(mf.createImmutable(data, lastIndex, currentIndex - lastIndex))) { // breaks if the remaining markers are not required in the script
                    currentIndex = currentIndex + tokenIndex;
                    lastIndex = currentIndex;
                } else {
                    endReached = true;
                    break;
                }
            } else
                currentIndex++;
        }
        if (!endReached && lastIndex < length + 1)
            markers.add(mf.createImmutable(data, lastIndex, this.length - (lastIndex - 1)));
        return markers;
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
