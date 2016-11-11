package com.flytxt.tp.translator;

import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.marker.MarkerFactory;

public abstract class TpAbsTest {
	protected MarkerFactory markerFactory = new MarkerFactory();


	protected Marker getMarker(String str) {
		byte[] lineMarker = str.getBytes();
		markerFactory.getCurrentObject().setCurrentLine(lineMarker, 0, lineMarker.length);
		Marker line = markerFactory.getLineMarker();
		return line;
	}
}
