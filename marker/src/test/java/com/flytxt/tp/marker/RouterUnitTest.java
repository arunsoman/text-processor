package com.flytxt.tp.marker;

import org.junit.Test;

import com.flytxt.tp.marker.Router;

import junit.framework.Assert;

public class RouterUnitTest {
	@Test
	public void test1() {
		int[] input = {5,4,6};
		int[] indexPositionPtr= {4,5,6};
		int [] markerNATPosition=    {1,0,2};
		Router r = new Router(input);
		validate(r, input, indexPositionPtr, markerNATPosition);
	}
	
	@Test
	public void test2() {
		int[] input = {4,6, 9};
		int[] indexPositionPtr= {4,6,9};
		int [] markerNATPosition=    {0,1,2};
		Router r = new Router(input);
		validate(r, input, indexPositionPtr, markerNATPosition);
	}
	
	@Test
	public void test3() {
		int[] input = {9,6,4};
		int[] indexPositionPtr= {4,6,9};
		int [] markerNATPosition=    {2,1,0};
		Router r = new Router(input);
		validate(r, input, indexPositionPtr, markerNATPosition);
	}
	
	private void validate(Router r,int[] input,  int[] indexPositionPtr, int[] markerNATPosition){
		for(int i = 0; i < input.length; i ++){
			if(r.geNthtMarkerlocation(i) != indexPositionPtr[i]){
				Assert.failNotEquals("should be same", indexPositionPtr[i], r.geNthtMarkerlocation(i));
			}
		}
		for(int i = 0; i < input.length; i ++){
			if(r.getMarkerPosition(i) != markerNATPosition[i]){
				Assert.failNotEquals("should be same", markerNATPosition[i], r.getMarkerPosition(i));
			}
		}
	}
}
