package com.flytxt.parser.translator;

import org.junit.Before;

import com.flytxt.parser.marker.CurrentObject;
import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;

public abstract class TpAbsTest {
	protected MarkerFactory markerFactory = new MarkerFactory();
	protected CurrentObject currentObject = new CurrentObject();

	@Before
	public void init() {
		markerFactory.init(currentObject);
	}

	protected Marker getMarker(String str) {
		byte[] lineMarker = str.getBytes();
		currentObject.setCurrentLine(lineMarker, 0, lineMarker.length);
		Marker line = markerFactory.createMarker(lineMarker,0, lineMarker.length);
		return line;
	}
}
