package com.flytxt.tp.marker;

import org.junit.Test;

public class MakerPerfTest {
	MarkerFactory mf = new MarkerFactory();
	private Marker[] create(String str, int size, boolean cache){
		for(int i = 0; i < size; i++){
			Marker longM = mf.createMarker(i+str);
		}
		mf.reclaim();
		Marker[] markers = new Marker[size];
		for(int i = 0; i < size; i++){
			Marker longM = mf.createMarker(i+str);
			longM.cacheValue = cache;
			markers[i] = longM;
		}
		return markers;
	}
	private long testCacheLongRead(int size){
		Marker[] markers = create("3243", size, true);
		long startTime = System.currentTimeMillis();
		for(int i = 0; i < size; i++){
			for(int j = 0; j <5; j++){
				 markers[i].asLong();
			}
		}
		long endTime = System.currentTimeMillis();
		return endTime - startTime;
	}
	
	private long testNonCacheLongRead(int size){
		//load mf.pool
		Marker[] markers = create("3243", size, false);
		long startTime = System.currentTimeMillis();
		for(int i = 0; i < size; i++){
			for(int j = 0; j <5; j++){
				 markers[i].asLong();
			}
		}
		long endTime = System.currentTimeMillis();
		return endTime - startTime;
	}
	
	private long testCacheDoubleRead(int size){
		Marker[] markers = create("32.43", size, true);
		long startTime = System.currentTimeMillis();
		for(int i = 0; i < size; i++){
			for(int j = 0; j <5; j++){
				 markers[i].asDouble();
			}
		}
		long endTime = System.currentTimeMillis();
		return endTime - startTime;
	}
	
	private long testNonCacheDoubleRead(int size){
		Marker[] markers = create("32.43", size, false);
		long startTime = System.currentTimeMillis();
		for(int i = 0; i < size; i++){
			for(int j = 0; j <5; j++){
				 markers[i].asDouble();
			}
		}
		long endTime = System.currentTimeMillis();
		return endTime - startTime;
	}
	
	@Test
	public void testLong(){
		int size = 1000000;
		long report1 = testNonCacheLongRead(size);
		long report2 = testCacheLongRead(size);
		System.out.println("diff in sec"+(report1 - report2));
	}
	@Test
	public void testDouble(){
		int size = 1000000;
		long report1 = testNonCacheDoubleRead(size);
		long report2 = testCacheDoubleRead(size);
		System.out.println("diff in sec"+(report1 - report2));
	}


}
