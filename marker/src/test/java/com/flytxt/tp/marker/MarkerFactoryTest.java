package com.flytxt.tp.marker;

import org.junit.Test;

import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.marker.MarkerFactory;

import junit.framework.Assert;

public class MarkerFactoryTest {
	MarkerFactory markerFactory = new MarkerFactory();
	@Test
    public void createMarkerInt(){
    	int value = 9987654;
    	Marker mInt = markerFactory.createMarker(value);
    	if(value != mInt.asInt())
    		Assert.assertEquals(value, mInt.asInt());
    	if(String.valueOf(value).equals(mInt.toString()))
    		Assert.assertEquals(String.valueOf(value), mInt.toString());
    }
	@Test
    public void createLongAndReadAsInt(){
    	String value = "43453453453345345654";
    	Marker strM = markerFactory.createMarker(value);
    	try{
    	int iVal = strM.asInt();
    	Assert.fail();
	}catch(RuntimeException e){
		}
	}
	
	@Test
    public void createInvalidLongAndReadAsLong(){
    	String value = "43666666666666666664534534533453456548888888888888888";
    	Marker strM = markerFactory.createMarker(value);
    	try{
    	long lVal = strM.asLong();
    	Assert.fail();
	}catch(RuntimeException e){
		}
	}
	@Test
    public void createMarkerDouble(){
    	double value = 998.7654;
    	Marker mInt = markerFactory.createMarker(value);
    	if(value != mInt.asDouble())
    		Assert.assertEquals(value, mInt.asDouble());
    	if(String.valueOf(value).equals(mInt.toString()))
    		Assert.assertEquals(String.valueOf(value), mInt.toString());
    }

	@Test
    public void createMarkerString(){
    	String value = "998765-- 8 4";
    	Marker mInt = markerFactory.createMarker(value);
    	if(String.valueOf(value).equals(mInt.toString()))
    		Assert.assertEquals(value, mInt.toString());
    }
	
	@Test
    public void createMarkerBytes(){
    	String value = "998765-- 8 4";
    	byte[] data = value.getBytes();
    	Marker mInt = markerFactory.createMarker(data,0, data.length);
    	if(String.valueOf(value).equals(mInt.toString()))
    		Assert.assertEquals(value, mInt.toString());
    }
	
	@Test
	public void resetMarkerPool(){
		String value = "998765-- 8 4";
    	Marker mInt = markerFactory.createMarker(value);
    	 value = "-998765-- 8 4";
    	 mInt = markerFactory.createMarker(value);
    	
    	markerFactory.reclaim();
    	value = "-998765-- 8 4";
   	 mInt = markerFactory.createMarker(value);
   	Assert.assertEquals(2, markerFactory.getMarkerPoolSize());
	}
}
