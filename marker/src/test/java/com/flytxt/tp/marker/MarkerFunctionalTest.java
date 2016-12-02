package com.flytxt.tp.marker;

import org.junit.Test;

import com.flytxt.tp.marker.ConstantMarker;
import com.flytxt.tp.marker.Marker;

import junit.framework.Assert;

public class MarkerFunctionalTest extends TestConstruct implements ConstantMarker {
	final String str = "1,22,1,1,,45,30,False$2011-11-11T12:00:00-05:00+FAlse*,,,-False^False,1,0,,,,,,,,,,,1,1";

	@Test
	public void testFalseAsToken() {
		final Marker line = getLineMarker(str);
		Marker m1 = markerFactory.createMarker(null, 0, 0);
		Marker m2 = markerFactory.createMarker(null, 0, 0);
		Marker m3 = markerFactory.createMarker(null, 0, 0);
		int[] indices = new int[] { 1, 2 };
		Router r = new Router(indices);
		line.splitAndGetMarkers("False".getBytes(), r, markerFactory, m1, m2);
		validate("False", indices, str, m1, m2);
	}

	@Test
	public void testInvalidLong() {
		Marker m = markerFactory.createMarker("1bc78");
		try {
			long d = m.asLong();
			Assert.fail();
		} catch (NumberFormatException e) {
		}
	}

	@Test
	public void testGetData(){
		String str = "hi";
		Marker m = markerFactory.createMarker(str);
		byte[] data = m.getData();
		String result = new String(data, m.index, m.length);
		if(!str.equals(result)){
			Assert.assertEquals(str, result);
		}
		str = "97";
		 long value = Long.parseLong(str);
		 m = markerFactory.createMarker(value);
		 data = m.getData();
		result = new String(data, m.index, m.length);
		if(!str.equals(result)){
			Assert.assertEquals(str, result);
			
		}
		str = "9.7";
		 double dValue = Double.parseDouble(str);
		 m = markerFactory.createMarker(dValue);
		 data = m.getData();
		result = new String(data, m.index, m.length);
		Assert.assertEquals(dValue, Double.parseDouble(result), 0001);
	}

	@Test
	public void testInvalidDouble() {
		Marker m = markerFactory.createMarker("1bc.78");
		try {
			double d = m.asDouble();
			Assert.fail();
		} catch (NumberFormatException e) {
			// TODO: handle exception
		}
	}

	@Test
	public void testWithoutLineMarker() {
		final Marker line = getLineMarker(str);
		Marker m1 = markerFactory.createMarker(str);
		Marker m2 = markerFactory.createMarker(null, 0, 0);
		Marker m3 = markerFactory.createMarker(null, 0, 0);
		int[] indices = new int[] { 1, 2 };
		Router r = new Router(indices);
		m1.splitAndGetMarkers("False".getBytes(), r, markerFactory, m2, m3);
		validate("False", indices, str, m2, m3);
	}

	@Test
	public void testFalse() {
		final Marker line = getLineMarker(str);
		Marker m1 = markerFactory.createMarker(null, 0, 0);
		Marker m2 = markerFactory.createMarker(null, 0, 0);
		Marker m3 = markerFactory.createMarker(null, 0, 0);
		int[] indices = new int[] { 0, 2 };
		Router r = new Router(indices);
		line.splitAndGetMarkers("False".getBytes(), r, markerFactory, m1, m2);
		validate("False", indices, str, m1, m2);
	}

	@Test
	public void testWith0() {
		final Marker line = getLineMarker(str);
		Marker m1 = markerFactory.createMarker(null, 0, 0);
		int[] indices = new int[] { 0 };
		Router r = new Router(indices);
		line.splitAndGetMarkers(",".getBytes(), r, markerFactory, m1);
		validate(",", indices, str, m1);
	}

	@Test
	public void test1() {
		final Marker line = getLineMarker(str);
		Marker m1 = markerFactory.createMarker(null, 0, 0);
		Marker m2 = markerFactory.createMarker(null, 0, 0);
		Marker m3 = markerFactory.createMarker(null, 0, 0);
		int[] indices = new int[] { 1, 5 };
		Router r = new Router(indices);
		line.splitAndGetMarkers(",".getBytes(), r, markerFactory, m1, m2);
		validate(",", indices, str, m1, m2);
	}

	/**
	 * this is not supported yet, need new data structure
	 */
	// @Test
	public void testSameMarker2() {
		final Marker line = getLineMarker(str);
		Marker m1 = markerFactory.createMarker(null, 0, 0);
		Marker m2 = markerFactory.createMarker(null, 0, 0);
		int[] indices = new int[] { 1, 1 };
		Router r = new Router(indices);
		line.splitAndGetMarkers(",".getBytes(), r, markerFactory, m1, m2);
		validate(",", indices, str, m1, m2);
	}

	@Test
	public void test3() {
		final Marker line = getLineMarker(str);
		Marker m1 = markerFactory.createMarker(null, 0, 0);
		Marker m2 = markerFactory.createMarker(null, 0, 0);
		Marker m3 = markerFactory.createMarker(null, 0, 0);
		int[] indices = new int[] { 4, 1 };
		Router r = new Router(indices);
		line.splitAndGetMarkers(",".getBytes(), r, markerFactory, m1, m2);
		validate(",", indices, str, m1, m2);
	}

	@Test
	public void test4() {
		final Marker line = getLineMarker(str);
		Marker m1 = markerFactory.createMarker(null, 0, 0);
		Marker m2 = markerFactory.createMarker(null, 0, 0);
		Marker m3 = markerFactory.createMarker(null, 0, 0);
		int[] indices = new int[] { 4, 1, 9 };
		Router r = new Router(indices);
		line.splitAndGetMarkers(",".getBytes(), r, markerFactory, m1, m2, m3);
		validate(",", indices, str, m1, m2, m3);
	}

	@Test
	public void test5() {
		final Marker line = getLineMarker(str);
		Marker m1 = markerFactory.createMarker(null, 0, 0);
		Marker m2 = markerFactory.createMarker(null, 0, 0);
		Marker m3 = markerFactory.createMarker(null, 0, 0);
		int[] indices = new int[] { 7 };
		Router r = new Router(indices);
		line.splitAndGetMarkers(",".getBytes(), r, markerFactory, m1);
		validate(",", indices, str, m1);
		indices = new int[] { 2, 1 };
		r = new Router(indices);
		m1.splitAndGetMarkers("-".getBytes(), r, markerFactory, m2, m3);
		validate("-", indices, m1.toString(), m2, m3);
	}

	@Test
	public void testArrayOutOfBound1() {
		final Marker line = getLineMarker(str);
		Marker m1 = markerFactory.createMarker(null, 0, 0);
		Marker m2 = markerFactory.createMarker(null, 0, 0);
		Marker m3 = markerFactory.createMarker(null, 0, 0);
		int[] indices = new int[] { 7 };
		Router r = new Router(indices);
		line.splitAndGetMarkers(",".getBytes(), r, markerFactory, m1);
		validate(",", indices, str, m1);
		indices = new int[] { 0, 19 };
		r = new Router(indices);
		m1.splitAndGetMarkers("-".getBytes(), r, markerFactory, m2, m3);
		validate("-", indices, m1.toString(), m2, m3);
	}

}
