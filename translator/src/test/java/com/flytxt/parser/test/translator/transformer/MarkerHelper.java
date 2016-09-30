package com.flytxt.parser.test.translator.transformer;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;

public class MarkerHelper {
	private static MarkerFactory mf = new MarkerFactory();
	private String str;
	
	public byte[] getBytes(){
		return str.getBytes();
	}
	public MarkerFactory getMf(){
		return mf;
	}
	public Marker getMarker(){
		return mf.create(0, str.getBytes().length);
	}

	public MarkerHelper(String str) {
		super();
		this.str = str;
	}
}
