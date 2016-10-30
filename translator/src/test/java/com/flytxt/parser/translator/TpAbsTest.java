package com.flytxt.parser.translator;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.flytxt.parser.marker.CurrentObject;
import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;

@Component
public abstract class TpAbsTest {
	@Autowired
	protected MarkerFactory markerFactory;
	
	protected CurrentObject currentObject;

	@Before
	public void init() {
		currentObject = markerFactory.getCurrentObject();
		currentObject.setFileName("TestFile");
		currentObject.setFolderName("TestFolder");
	}

	protected Marker getMarker(String str) {
		byte[] lineMarker = str.getBytes();
		currentObject.setLineMarker(lineMarker);
		currentObject.setCurrentLine(0, lineMarker.length);
		Marker line = markerFactory.createMarker(lineMarker,0, lineMarker.length);
		return line;
	}
}
