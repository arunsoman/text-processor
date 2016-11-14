package com.flytxt.tp.marker;

public class Marker implements Comparable<byte[]> {

    public int index;

    public int length;
    private FindMarker fm = new FindMarker();
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
        find(false,data, token, indexOfMarker, mf, markers);
    }

    private void resetMarkerLength(Marker...markers){
    	for(Marker aMarker: markers){
    		aMarker.length = 0;
    	}
    }
    protected void find(boolean assignData, byte[] data, byte[] token, int[] indexOfMarker, MarkerFactory mf, Marker... markers) {
    	resetMarkerLength(markers);
    	
        Router router = mf.findRouter(indexOfMarker);
        int markers2Mine = router.maxMarkers2Mine();
        
        if(token.length==1)
        	fromByteArray(assignData, markers2Mine, token[0], data, router, markers);
        else
        	fromByteArray(assignData, markers2Mine, token, data, router, markers);
        System.out.println("\n");
    }

    private void fromByteArray(boolean assignData, int markers2Mine, byte token, byte[] data, Router router, Marker...markers){
    	int eol = this.index + length;
        int from = this.index;
        int stx = this.index;
        
    	int counter = 0;
        for(int i =0; i <= markers2Mine; i++){
        	from = fm.findPreMarker(token, from+1, eol, data);
        	if(from == -1){//there is no marker hence consider the whole ",NoCommaAfterThis."
        		from = eol;
        	}
        	int len = from-stx;
        	if(len< 0){
        		stx = 0;
        		len = 0;
        	}
//        	System.out.println("{M:"+i +" from:"+from +" str: "+new String(data, stx, len) +" } ");
        	int nextPos = router.geNthtMarkerlocation(counter);
        	if(i == nextPos){
        		int ptr = router.getMarkerPosition(counter);
        		Marker m = markers[ptr];
        		m.setLineAttribute(stx, len);
        		if(assignData)
        			((ImmutableMarker)m).setData(data);
    			counter++;
        	}
        	stx = from+1;
        }
    	
    }
    private void fromByteArray(boolean assignData, int markers2Mine, byte[] token, byte[] data, Router router, Marker...markers){
    	int eol = this.index + length;
        int from = this.index;
        int stx = this.index;
        
    	int counter = 0;
        for(int i =0; i <= markers2Mine; i++){
        	from = fm.findPreMarker(token, from+1, eol, data);
        	int len = from-stx-token.length;
        	System.out.println("{M:"+i +" from:"+from +" str: "+new String(data, stx, len) +" } ");
        	int nextPos = router.geNthtMarkerlocation(counter);
        	if(i == nextPos){
        		int ptr = router.getMarkerPosition(counter);
        		Marker m = markers[ptr];
        		m.setLineAttribute(stx, len);
        		if(assignData)
        			((ImmutableMarker)m).setData(data);
    			counter++;
        	}
        	stx = from;
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