package com.flytxt.parser.translator;

import com.flytxt.parser.marker.Marker;

public class Translator implements TpConstant {
	public static final byte[] asByteArray(double lVal){
		//need to find cleaner way
		return String.valueOf(lVal).getBytes();
	}
	public static final byte[] asByteArray(long lVal){
		//need to find cleaner way
		return String.valueOf(lVal).getBytes();
	}
	public static final long asLong(byte[] data, Marker m){
		if(m.getData() != null)
			data = m.getData();
		return Long.parseLong(m.toString(data));
	}
	public static final double asDouble(byte[] data,Marker m){
		if(m.getData() != null)
			data = m.getData();
		return Double.parseDouble(m.toString(data));
	}
}
