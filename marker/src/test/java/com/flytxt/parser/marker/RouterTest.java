package com.flytxt.parser.marker;

import org.junit.Test;

import com.flytxt.tp.marker.Router;

import junit.framework.Assert;

public class RouterTest {
	@Test
	public void test1() {
		int[] input = {5,4,6};
		int[] indexPositionPtr= {4,5,6};
		int [] markerNATPosition=    {1,0,2};
		Router r = new Router();
		r.set(input);
		for(int i = 0; i < input.length; i ++){
			if(r.geNthtMarkerlocation(i) != indexPositionPtr[i]){
				Assert.failNotEquals("should be same", indexPositionPtr[i], r.geNthtMarkerlocation(i));
			}
		}
		for(int i = 0; i < input.length; i ++){
			if(r.geNthtMarkerlocation(i) != indexPositionPtr[i]){
				Assert.failNotEquals("should be same", markerNATPosition[i], r.getMarkerPosition(i));
			}
		}
		//System.out.println(r);
	}
}
