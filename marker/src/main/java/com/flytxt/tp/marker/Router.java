package com.flytxt.tp.marker;

import java.util.Arrays;

public final class Router implements Comparable<int[]> {

    private int[] markerNATPosition;
    private int[] markerPosition;//input
    private int[] indexPositionPtr;

    public String toString(){
    	StringBuilder sb = new StringBuilder();
    	toStr(sb, "markerPosition", markerPosition);
    	toStr(sb, "indexPositionPtr", indexPositionPtr);
    	toStr(sb, "markerNATPosition", markerNATPosition);
    	sb.append("summary\n");
    	for(int i = 0; i < markerNATPosition.length; i++){
    		sb.append("index: ").append(i).append("pick split : ").append(geNthtMarkerlocation(i)).append(" assign to : ").append(getMarkerPosition(i)).append("\n");
    	}
    	return sb.toString();
    }
    /***
     *  546 -> markerPosition/input
     *  456=> indexPositionPtr
     *  102-> markerNATPosition
     */
    
    public int maxMarkers2Mine(){
    	return indexPositionPtr[indexPositionPtr.length-1];
    }
    public void set(int[] markerPosition) {
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
		throw new RuntimeException("could noy find location for "+i +"in array"+markerPosition);
	}

	public int geNthtMarkerlocation(int index){
    	return indexPositionPtr[index]; //"java starts with 0"
    }

	public int getMarkerPosition(int locationIndex){
		return markerNATPosition[locationIndex];
	}
    @Override
    public int compareTo(int[] o) {
        if (markerPosition.length != o.length)
            return -1;
        for (int i = 0; i < o.length; i++)
            if (markerPosition[i] != o[i])
                return -1;
        return 0;
    }

    private void toStr(StringBuilder sb,String name, int [] array){
    	sb.append(name).append("=>");
    	for(int i = 0; i < array.length; i++){
    		sb.append(array[i]).append(",");
    	}	
    	sb.append("\n");
    }
}