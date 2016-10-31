package com.flytxt.parser.translator;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;

public abstract class TpAbsTest {
	protected MarkerFactory markerFactory = new MarkerFactory();


	protected Marker getMarker(String str) {
		byte[] lineMarker = str.getBytes();
		markerFactory.getCurrentObject().setCurrentLine(lineMarker, 0, lineMarker.length);
		Marker line = markerFactory.createMarker(lineMarker,0, lineMarker.length);
		return line;
	}
}
