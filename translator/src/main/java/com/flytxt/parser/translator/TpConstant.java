package com.flytxt.parser.translator;

import com.flytxt.parser.marker.ImmutableMarker;
public interface TpConstant {

	byte negative = (byte)'-';
	byte start = 0x30;
	byte end = 0x39;
	byte trueToken = (byte)'T';
	byte falseToken = (byte)'F';
	byte exp = (byte) ((byte)'e' -start);
	byte dot = (byte) (0x2e -start);
	ImmutableMarker booleanTrueMarker = new ImmutableMarker("T".getBytes());
	ImmutableMarker booleanFalseMarker = new ImmutableMarker("F".getBytes());
	
}
