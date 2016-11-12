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
        int currentIndex = this.index;
        Router router = mf.findRouter(indexOfMarker);
        int i =0;
        for(; i < token.length; i++){
        	if(data[index+i] != token[i]){
        		break;
        	}
        }
        int stIndex, len = 0, count =0;
        currentIndex = (i == token.length)? token.length:this.index;
        stIndex = currentIndex;
        while (currentIndex < this.index + length) {
        	if(data[currentIndex] == token[0]){//probably a marker
        		int stage = len;
        		for(i = 1; i < token.length; i++){//is the rest part of token?
                	if(data[currentIndex+i] != token[i]){
                		break;
                	}
                }
        		if(i == token.length){//found marker
        			//check if this marker is needed?
 
        			//either way reset stIndex and len
        			len = 0;
        			stIndex = currentIndex;
        			Marker m = mf.createMarker(data, stIndex, stage);
        			int ptr = router.getMarkerPosition(count);
        			markers[ptr] = m;
        			count ++;
        			if(count == markers.length)
        				return;
        		}
        		else{//false alarm
        			len = currentIndex;
        		}
        		
        	}
        	else{//move on
        		len++;
        	}
        	currentIndex++;
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
