package com.flytxt.parser.translator;

import com.flytxt.parser.marker.Marker;

public class TpLogic implements TpConstant{
	public Marker and(Marker m1, Marker m2){
		throw new RuntimeException();
	}
	
	public Marker or(Marker m1, Marker m2){
		throw new RuntimeException();
	}
	
	public Marker not(Marker m1){
		throw new RuntimeException();
	}
}
