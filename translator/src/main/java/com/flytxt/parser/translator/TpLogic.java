package com.flytxt.parser.translator;

import com.flytxt.parser.marker.Marker;

public class TpLogic implements TpConstant{
	private final int ff = (falseToken*2);
	private final int tt = (trueToken*2);
	public Marker and(Marker m1, Marker m2){
		return ((m1.getData()[0] + m2.getData()[0]) != tt)? booleanFalseMarker:booleanTrueMarker;
	}
	
	public Marker or(Marker m1, Marker m2){
		return ((m1.getData()[0] + m2.getData()[0]) == ff)? booleanFalseMarker:booleanTrueMarker;
	}
	
	public Marker not(Marker m1){
		return(m1.getData()[0] == trueToken)?booleanFalseMarker:booleanTrueMarker;
	}
}
