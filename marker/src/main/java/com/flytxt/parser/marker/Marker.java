package com.flytxt.parser.marker;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Configuration
@Qualifier("marker")
public class Marker {

    public int index;

    public int length;

    private CurrentObject currentObject;
    
    public void setData(CurrentObject currentObject){
    	this.currentObject = currentObject;
    }
    
    public void setLineAttribute(int index, int length){
    	this.index = index;
    	this.length = length;
    }
    
    public void splitAndGetMarkers( final byte[] token, final int[] indexOfMarker, final MarkerFactory mf, Marker... markers) {
    	byte[] data = currentObject.getLineMarker();
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

    public Marker splitAndGetMarker(final byte[] token, final int indexOfMarker, final MarkerFactory mf) {
    	byte[] data = currentObject.getLineMarker();
        int count = 1, lastIndex = index, currentIndex = index, tokenIndex;
        while (currentIndex < index + length) {
            for (tokenIndex = 0; tokenIndex < token.length && token[tokenIndex] == data[currentIndex + tokenIndex]; tokenIndex++)
                ;// loop to check if token is present at position currentIndex
            if (tokenIndex == token.length) { // true if token found at currentIndex
                if (indexOfMarker == count++)
                    return mf.createMarker(null,lastIndex, currentIndex - lastIndex);
                currentIndex = currentIndex + token.length;
                lastIndex = currentIndex;
            } else
                currentIndex++;
        }

        if (lastIndex < length + 1 && indexOfMarker == count)
            return mf.createMarker(null,lastIndex, this.length - lastIndex);
        return null;
    }

    public FlyList<Marker> splitAndGetMarkerList(final byte[] token, final MarkerFactory mf) {
    	byte[] data = currentObject.getLineMarker();
        final FlyList<Marker> markers = mf.getArrayList();
        int currentIndex = index, lastIndex = index, tokenIndex;
        boolean endReached = false;
        while (currentIndex < index + length) {
            for (tokenIndex = 0; tokenIndex < token.length && token[tokenIndex] == data[currentIndex + tokenIndex]; tokenIndex++)
                ;
            if (tokenIndex == token.length) {
                try{
                	markers.add(mf.createMarker(null,lastIndex, currentIndex - lastIndex)) ; // breaks if the remaining markers are not required in the script
                    currentIndex = currentIndex + tokenIndex;
                    lastIndex = currentIndex;
                } catch(ArrayIndexOutOfBoundsException e) {
                    endReached = true;
                    break;
                }
            } else
                currentIndex++;
        }
        if (!endReached && lastIndex < length + 1)
            try{
            	markers.add(mf.createMarker(null,lastIndex, this.length - lastIndex));
            } catch (Exception e) {
				// TODO: handle exception
			}
        return markers;
    }

    public byte[] getData() {
        return currentObject.getLineMarker();
    }

    public String toString() {
        return new String(currentObject.getLineMarker(), index, length);
    }
}
