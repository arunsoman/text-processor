package com.flytxt.tp.translator.feature.transformer;

import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.marker.MarkerFactory;

public class MarkerHelper {
	
	private String str;
	
	public byte[] getBytes(){
		return str.getBytes();
	}
	
	public Marker getMarker(MarkerFactory mf){
		byte[] data = str.getBytes();
		mf.getCurrentObject().setCurrentLine(data, 0, str.length());
		return mf.createMarker(null,0, data.length);
	}

	public MarkerHelper(String str) {
		super();
		this.str = str;
	}
}
