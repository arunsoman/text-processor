package com.flytxt.tp.translator;

import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.marker.ConstantMarker;

public class Translator implements ConstantMarker {
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
