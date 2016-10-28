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
	public static final long asLong( Marker m){
		return Long.parseLong(m.toString());
	}
	public static final double asDouble(Marker m){
		return Double.parseDouble(m.toString());
	}
}
