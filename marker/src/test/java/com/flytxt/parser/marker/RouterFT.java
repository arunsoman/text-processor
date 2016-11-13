package com.flytxt.parser.marker;

import org.junit.Test;

import com.flytxt.tp.marker.ConstantMarker;
import com.flytxt.tp.marker.ImmutableMarker;
import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.marker.MarkerFactory;
import com.flytxt.tp.marker.Router;

import junit.framework.Assert;

public class RouterFT {
	@Test
	public void test1() {
		MarkerFactory mf = new MarkerFactory();
		String str = "0123456789";
		byte[] data = str.getBytes();
		mf.getCurrentObject().setCurrentLine(data, 0, data.length);
		Marker line = mf.getLineMarker();
		Marker m0 = mf.createMarker(null, 0, 0);
		Marker m1 = mf.createMarker(null, 0, 0);
		Marker m2 = mf.createMarker(null, 0, 0);
		int[] input = {5,4,6};
		function(input, line, m0,m1,m2);
		Assert.assertEquals("check equals", String.valueOf(input[0]), m0.toString());
		Assert.assertEquals("check equals", String.valueOf(input[1]), m1.toString());
		Assert.assertEquals("check equals", String.valueOf(input[2]), m2.toString());
	}
	
	@Test
	public void test2() {
		MarkerFactory mf = new MarkerFactory();
		String str = "0123456789";
		byte[] data = str.getBytes();
		mf.getCurrentObject().setCurrentLine(data, 0, data.length);
		Marker line = mf.getLineMarker();
		Marker m0 = mf.createMarker(null, 0, 0);
		Marker m1 = mf.createMarker(null, 0, 0);
		Marker m2 = mf.createMarker(null, 0, 0);
		int[] input = {0,4,9};
		function(input, line, m0,m1,m2);
		Assert.assertEquals("check equals", String.valueOf(input[0]), m0.toString());
		Assert.assertEquals("check equals", String.valueOf(input[1]), m1.toString());
		Assert.assertEquals("check equals", String.valueOf(input[2]), m2.toString());
	}
	@Test
	public void test3() {
		MarkerFactory mf = new MarkerFactory();
		String str = "0123456789";
		byte[] data = str.getBytes();
		mf.getCurrentObject().setCurrentLine(data, 0, data.length);
		Marker line = mf.getLineMarker();
		Marker m0 = mf.createMarker(null, 0, 0);
		Marker m1 = mf.createMarker(null, 0, 0);
		Marker m2 = mf.createMarker(null, 0, 0);
		int[] input = {9,4,0};
		function(input, line, m0,m1,m2);
		Assert.assertEquals("check equals", String.valueOf(input[0]), m0.toString());
		Assert.assertEquals("check equals", String.valueOf(input[1]), m1.toString());
		Assert.assertEquals("check equals", String.valueOf(input[2]), m2.toString());
	}
	
	private void function(int[] input, Marker line, Marker...markers){
		Router r = new Router();
		r.set(input);
		for(int i = 0; i < input.length; i++){
			Marker m = markers[r.getMarkerPosition(i)];
			m.setLineAttribute(r.geNthtMarkerlocation(i), 1);
		}
	}
}
