package com.flytxt.tp.marker;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

import lombok.Getter;

public class Marker {

	boolean cacheValue = true;
	public int index;

	public int length;

	private FindMarker fm = new FindMarker();
	private long lastModifiedTime;
	private long lastReadTime;
	@Getter
	private int dataType;

	private long longValue;
	private double doubleValue;
	private CurrentObject currentObject;
	private byte[] localData;

	public static final int longDataType = 1;
	public static final int doubleDataType = 2;
	public static final int lineDataType = 3;
	public static final int localDataType = 4;
	final static int maxDigitsInLong = String.valueOf(Long.MAX_VALUE).length();
	Marker(long l) {
		set(l);
	}

	Marker(double d) {
		set(d);
	}

	Marker(byte[] data, int index, int length) {
		set(data, index, length);
	}

	Marker(CurrentObject currentObject) {
		this.currentObject = currentObject;
		dataType = lineDataType;
		lastModifiedTime = lastReadTime+1;
	}

	void set(int index, int length) {
		this.index = index;
		this.length = length;
		localData = null;
		dataType = lineDataType;
		lastModifiedTime = lastReadTime+1;
	}

	void set(byte[] data, int index, int length) {
		localData = data;
		this.index = index;
		this.length = length;
		dataType = localDataType;
		lastModifiedTime = lastReadTime+1;
	}
    
	void set(long lvalue) {
		longValue = lvalue;
		dataType = longDataType;
		lastModifiedTime = lastReadTime+1;
	}

	void set(double dvalue) {
		doubleValue = dvalue;
		dataType = doubleDataType;
		lastModifiedTime = lastReadTime+1;
	}

	void reset() {
		localData = null;
		dataType = 0;
		length = 0;
		lastModifiedTime = lastReadTime =0;
	}

	public void splitAndGetMarkers(final byte[] token, final Router r, final MarkerFactory mf, Marker... markers) {
		if (dataType == lineDataType) {
			byte[] data = currentObject.getLine();
			find(false, data, token, r, mf, markers);
		} else {
			find(true, getData(), token, r, mf, markers);
		}
	}

	private void resetMarkerLength(Marker... markers) {
		for (Marker aMarker : markers) {
			aMarker.reset();
		}
	}

	private void find(boolean assignData, byte[] data, byte[] token, final Router router, MarkerFactory mf,
			Marker... markers) {
		resetMarkerLength(markers);
		if (token.length == 1)
			fromByteArray(assignData, token[0], data, router, markers);
		else
			fromByteArray(assignData, token, data, router, markers);
		// System.out.println("\n");
	}

	private void fromByteArray(boolean assignData, byte token, byte[] data, Router router, Marker... markers) {
		int eol = this.index + length;
		int from = this.index;
		int stx = this.index;

		int markers2Mine = router.maxMarkers2Mine();
		int counter = 0;
		for (int i = 0; i <= markers2Mine; i++) {
			from = fm.findPreMarker(token, from + 1, eol, data);
			if (from == -1) {// there is no marker hence consider the whole
								// ",NoCommaAfterThis."
				from = eol;
			}
			int len = from - stx;
			if (len < 0) {
				stx = 0;
				len = 0;
			}
			// System.out.println("{M:"+i +" from:"+from +" str: "+new
			// String(data, stx, len) +" } ");
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

	private void fromByteArray(boolean assignData, byte[] token, byte[] data, Router router, Marker... markers) {
		int eol = this.index + length;
		int from = this.index;
		int stx = this.index;
		int markers2Mine = router.maxMarkers2Mine();
		int counter = 0;
		for (int i = 0; i <= markers2Mine; i++) {
			from = fm.findPreMarker(token, from + 1, eol, data);
			int len = from - stx - token.length;
			// System.out.println("{M:"+i +" from:"+from +" str: "+new
			// String(data, stx, len) +" } ");
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
		byte[] data;
		switch (dataType) {
		case longDataType:
			data = String.valueOf(longValue).getBytes();
			length = data.length;
			index = 0;
			break;
		case localDataType:
			data = localData;
			break;
		case doubleDataType:
			data = String.valueOf(doubleValue).getBytes();
			length = data.length;
			index = 0;
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
		switch (dataType) {
		case localDataType:
			return new String(localData, index, length);
		case longDataType:
			return String.valueOf(longValue);
		case doubleDataType:
			return String.valueOf(doubleValue);
		case lineDataType:
			return new String(currentObject.getLine(), index, length);
		}
		return null;
	}

	public int asInt() {
		if (dataType == longDataType)
			return (int) longValue;
		if(cacheValue)
			if(lastModifiedTime<lastReadTime)
			return (int)longValue;
		long lValue = asLong();
		if(lValue < Integer.MIN_VALUE || lValue > Integer.MAX_VALUE)
			throw new RuntimeException(lValue +" cant cast to int");
		return (int)lValue;
	}

	public long asLong() {
		if (dataType == longDataType )
			return longValue;
		if(dataType == doubleDataType)
			return (long)doubleValue;
		if(cacheValue)
			if(lastModifiedTime<lastReadTime)
			return longValue;
		
		if (length == 0) {
			return 0;
		}
		long value = 0;
		byte[] data = getData();
		if(length > maxDigitsInLong){
			throw new RuntimeException(new String(data, index, length)+" cant be Long");
		}
		value = Long.parseLong(new String(data, index, length));
		lastReadTime = lastModifiedTime+1;
		longValue = value;
		return value;
	}

	public double asDouble() {
		if (dataType == doubleDataType)
			return doubleValue;
		if(cacheValue)
			if(lastModifiedTime<lastReadTime)
			return doubleValue;
		if (dataType == longDataType)
			throw new RuntimeException("Type cast exception LongMarker to DoubleMarker");
		
		if (length == 0) {
			return 0;
		}
		lastReadTime = lastModifiedTime+1;
		doubleValue = Double.parseDouble(toString());;
		return doubleValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (cacheValue ? 1231 : 1237);
		result = prime * result + ((currentObject == null) ? 0 : currentObject.hashCode());
		result = prime * result + dataType;
		long temp;
		temp = Double.doubleToLongBits(doubleValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((fm == null) ? 0 : fm.hashCode());
		result = prime * result + index;
		result = prime * result + (int) (lastModifiedTime ^ (lastModifiedTime >>> 32));
		result = prime * result + (int) (lastReadTime ^ (lastReadTime >>> 32));
		result = prime * result + length;
		result = prime * result + Arrays.hashCode(localData);
		result = prime * result + (int) (longValue ^ (longValue >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		
		return ArrayUtils.isEquals(this.getData(), ((Marker)obj).getData());
	}
	
	
}