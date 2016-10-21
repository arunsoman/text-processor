package com.flytxt.parser.marker;

public class Marker {

    public int index;

    public int length;

    public void splitAndGetMarkers(final byte[] data, final byte[] token, final int[] indexOfMarker, final MarkerFactory mf, Marker... markers) {
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

    public Marker splitAndGetMarker(final byte[] data, final byte[] token, final int indexOfMarker, final MarkerFactory mf) {

        int count = 1, lastIndex = index, currentIndex = index, tokenIndex;
        while (currentIndex < index + length) {
            for (tokenIndex = 0; tokenIndex < token.length && token[tokenIndex] == data[currentIndex + tokenIndex]; tokenIndex++)
                ;// loop to check if token is present at position currentIndex
            if (tokenIndex == token.length) { // true if token found at currentIndex
                if (indexOfMarker == count++)
                    return mf.create(lastIndex, currentIndex - lastIndex);
                currentIndex = currentIndex + token.length;
                lastIndex = currentIndex;
            } else
                currentIndex++;
        }

        if (lastIndex < length + 1 && indexOfMarker == count)
            return mf.create(lastIndex, this.length - lastIndex);
        return null;
    }

    public FlyList<Marker> splitAndGetMarkers(final byte[] data, final byte[] token, final MarkerFactory mf) {

        final FlyList<Marker> markers = mf.getArrayList();
        int currentIndex = index, lastIndex = index, tokenIndex;
        boolean endReached = false;
        while (currentIndex < index + length) {
            for (tokenIndex = 0; tokenIndex < token.length && token[tokenIndex] == data[currentIndex + tokenIndex]; tokenIndex++)
                ;
            if (tokenIndex == token.length) {
                if (markers.add(mf.create(lastIndex, currentIndex - lastIndex))) { // breaks if the remaining markers are not required in the script
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
            markers.add(mf.create(lastIndex, this.length - lastIndex));
        return markers;
    }

    public byte[] getData() {
        return null;
    }

    public String toString(final byte[] b) {
        return new String(b, index, length);
    }
}
