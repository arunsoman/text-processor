package com.flytxt.tp.marker;

import java.util.Arrays;

public final class Router {

    private int[] markerNATPosition;
    private int[] markerPosition;//input
    private int[] indexPositionPtr;


    /***
     *  546 -> markerPosition/input
     *  456=> indexPositionPtr
     *  102-> markerNATPosition
     */
    
    public int maxMarkers2Mine(){
    	return indexPositionPtr[indexPositionPtr.length-1];
    }
    public Router (int[] markerPosition) {
        this.markerPosition  = markerPosition;
    	indexPositionPtr = new int[markerPosition.length];
        System.arraycopy(markerPosition, 0, this.indexPositionPtr, 0, markerPosition.length);
        Arrays.sort(indexPositionPtr);
        this.markerNATPosition = new int[markerPosition.length];
        for(int i = 0; i <markerPosition.length; i++){
        	markerNATPosition[i] = getMarkerLocation(indexPositionPtr[i], markerPosition);
        }
    }
        
    private int getMarkerLocation(int i, int[]markerPosition) {
		for(int j = 0; j  <markerPosition.length; j++){
			if(i == markerPosition[j])
				return j;
		}
		throw new RuntimeException("could not find location for "+i +"in array"+markerPosition);
	}

	public int geNthtMarkerlocation(int index){
    	return indexPositionPtr[index]; //"java starts with 0"
    }

	public int getMarkerPosition(int locationIndex){
		return markerNATPosition[locationIndex];
	}
    
}