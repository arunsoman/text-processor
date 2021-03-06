package com.flytxt.tp.marker;

import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.marker.MarkerFactory;

import junit.framework.Assert;

public abstract class TestConstruct {
	protected MarkerFactory markerFactory = new MarkerFactory();

	protected Marker getLineMarker(String str) {
		byte[] lineMarker = str.getBytes();
		markerFactory.getCurrentObject().init("dummy-Folder", "fileName");
		markerFactory.getCurrentObject().setCurrentLine(lineMarker, 0, lineMarker.length);
		Marker line = markerFactory.getLineMarker();
		return line;
	}

	protected Marker getImmutableMarker(String str) {
		byte[] lineMarker = str.getBytes();
		return markerFactory.createMarker(lineMarker, 0, lineMarker.length);
	}

	protected void validate(String token, int[] indices, String str, Marker... markers) {
		String splits[] = str.split(token);
		if (splits.length < indices.length) {
			Assert.assertEquals("split size can't be less than indies", splits.length, indices);
		}
		for (int i = 0; i < indices.length; i++) {
			String actualStr = new String(markers[i].getData(), markers[i].index, markers[i].length);
			try {
				if (!splits[indices[i]].equals(actualStr))
					Assert.assertEquals(splits[indices[i]], actualStr);
			} catch (ArrayIndexOutOfBoundsException e) {
				Assert.assertEquals(0, markers[i].length);
			}
		}
	}
}
