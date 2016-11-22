package com.flytxt.tp.marker;

public class Marker {

    public int index;

    public int length;

    private FindMarker fm = new FindMarker();


    private int dataType;
    
    private long longValue;
    private double doubleValue;
    private CurrentObject currentObject;
    private byte[] localData;
    
    public static final int longDataType = 1;
    public static final int doubleDataType = 2;
    public static final int lineDataType = 3;
    public static final int localDataType = 4;
    
    Marker(long l){
    		longValue =l;
    		dataType = longDataType;
    }
    
    Marker(double d){
    		doubleValue = d;
    		dataType = doubleDataType;
    }
    
    Marker(byte[] data, int index, int length) {
        localData = data;
        this.index = index;
        this.length = length;
        dataType = localDataType;
    }

    public Marker(CurrentObject currentObject) {
        this.currentObject = currentObject;
        dataType = lineDataType;
    }

    void set(int index, int length) {
        this.index = index;
        this.length = length;
        localData = null;
        dataType = lineDataType;
    }

    void set(byte[] data, int index, int length) {
        localData = data;
        this.index = index;
        this.length = length;
        dataType = localDataType;
    }
    
    void set(long lvalue){
    		longValue=lvalue;
    		dataType = longDataType;
    }

    void set(double dvalue){
    		doubleValue=dvalue;
    		dataType = doubleDataType;
    }
    void reset(){
    		localData = null;
    		dataType = 0;
    }
    public void splitAndGetMarkers(final byte[] token, final Router r, final MarkerFactory mf, Marker... markers) {
        if (localData == null) {
            byte[] data = currentObject.getLine();
            find(false, data, token, r, mf, markers);
        } else {
            find(true, localData, token, r, mf, markers);
        }
    }

    private void resetMarkerLength(Marker... markers) {
        for (Marker aMarker : markers) {
            aMarker.length = 0;
        }
    }

    private void find(boolean assignData, byte[] data, byte[] token, final Router router, MarkerFactory mf, Marker... markers) {
        resetMarkerLength(markers);
        if (token.length == 1)
            fromByteArray(assignData, token[0], data, router, markers);
        else
            fromByteArray(assignData, token, data, router, markers);
        // System.out.println("\n");
    }

    private void fromByteArray(boolean assignData,  byte token, byte[] data, Router router, Marker... markers) {
        int eol = this.index + length;
        int from = this.index;
        int stx = this.index;

        int markers2Mine = router.maxMarkers2Mine();
        int counter = 0;
        for (int i = 0; i <= markers2Mine; i++) {
            from = fm.findPreMarker(token, from + 1, eol, data);
            if (from == -1) {// there is no marker hence consider the whole ",NoCommaAfterThis."
                from = eol;
            }
            int len = from - stx;
            if (len < 0) {
                stx = 0;
                len = 0;
            }
            // System.out.println("{M:"+i +" from:"+from +" str: "+new String(data, stx, len) +" } ");
            int nextPos = router.geNthtMarkerlocation(counter);
            if (i == nextPos) {
                int ptr = router.getMarkerPosition(counter);
                Marker m = markers[ptr];
                m.set(stx, len);
                if (assignData)
                    m.set(data, stx, len);
                counter++;
            }
            stx = from + 1;
        }

    }

    private void fromByteArray(boolean assignData,  byte[] token, byte[] data, Router router, Marker... markers) {
        int eol = this.index + length;
        int from = this.index;
        int stx = this.index;
        int markers2Mine = router.maxMarkers2Mine();
        int counter = 0;
        for (int i = 0; i <= markers2Mine; i++) {
            from = fm.findPreMarker(token, from + 1, eol, data);
            int len = from - stx - token.length;
            // System.out.println("{M:"+i +" from:"+from +" str: "+new String(data, stx, len) +" } ");
            int nextPos = router.geNthtMarkerlocation(counter);
            if (i == nextPos) {
                int ptr = router.getMarkerPosition(counter);
                Marker m = markers[ptr];
                m.set(stx, len);
                if (assignData)
                    m.set(data, stx, len);
                counter++;
            }
            stx = from;
        }
    }

    public byte[] getData() {
    		byte[] data ;
    		switch(dataType){
    		case longDataType:
    			data = String.valueOf(longValue).getBytes();
    			length = data.length;
    			break;
    		case localDataType:
    			data = localData;
    			break;
    		case doubleDataType:
    			data = String.valueOf(doubleValue).getBytes();
    			length = data.length;
    			break;
    		case lineDataType:
    			data = currentObject.getLine();
    			break;
    		default:
    			data = new byte[0];
    			length = 0;
    		}
        return data;
    }

    @Override
    public String toString() {
        return new String(getData(), index, length);
    }

    public int asInt() {
    		if(dataType == longDataType)
    			return (int)longValue;
    		return (int)asLong();
    }

    public long asLong() {
    		if(dataType == longDataType)
			return longValue;
    		if (length == 0) {
            return 0;
        }
        long value = 0;
        byte[] data = getData();
        int power = length;
        for (int i = index; i < index + length; i++) {
            power = power - 1;
            value += Math.pow(10, power) * Character.getNumericValue(data[i]);
        }
        return value;
    }

    public double asDouble() {
    		if(dataType == doubleDataType)
    			return doubleValue;
    		
        if (length == 0) {
            return 0;
        }
        return Double.parseDouble(toString());
    }

	public boolean isDataLocal() {
		return localData!= null;
	}
}