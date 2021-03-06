package com.flytxt.tp.translator;

import org.junit.Ignore;
import org.junit.Test;

import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.marker.MarkerFactory;
import com.flytxt.tp.marker.Router;

import junit.framework.Assert;

public class TranslatorFT {
	final String str = 
"1.5,22,7.1,1,,45,30,False$2011-11-11T12:00:00-05:00+FAlse*,,,-False^False,1,0,,,,,,,,,,,1,1";

	@Test
	public void ftDouble(){
		MarkerFactory mf = new MarkerFactory();
		byte[] data = str.getBytes();
		mf.getCurrentObject().setCurrentLine(data, 0, data.length);
		Marker lineMarker = mf.getLineMarker();
		Marker m1 = mf.createMarker(null, 0, 0);
        Marker m2 = mf.createMarker(null, 0,0);
        Marker m3 = mf.createMarker(null, 0,0);
        Marker m4 = mf.createMarker(null, 0, 0);
        Marker m5 = mf.createMarker(null, 0, 0);        
        int [] indices = new int[]{0,2,5};
        Router r = new Router(indices);
        lineMarker.splitAndGetMarkers(",".getBytes(),r , mf, m1, m2, m4);
        TpMath m = new TpMath();
        m3 = m.addDouble(m1, m2, mf);
        try{
        		m5 = m.subDouble(m3, m4, mf);
        }catch(RuntimeException e){
	        	Assert.fail();
        }
        // 30 52 45 7
        double result = -36.4;
        Assert.assertEquals(result, m5.asDouble());
	}
	@Test
	public void ftLong(){
		MarkerFactory mf = new MarkerFactory();
		byte[] data = str.getBytes();
		mf.getCurrentObject().setCurrentLine(data, 0, data.length);
		Marker lineMarker = mf.getLineMarker();
		Marker m1 = mf.createMarker(null, 0, 0);
        Marker m2 = mf.createMarker(null, 0,0);
        Marker m3 = mf.createMarker(null, 0,0);
        Marker m4 = mf.createMarker(null, 0, 0);
        Marker m5 = mf.createMarker(null, 0, 0);        
        int [] indices = new int[]{1,6,5};
        Router r = new Router(indices);
        lineMarker.splitAndGetMarkers(",".getBytes(),r , mf, m1, m2, m4);
        TpMath m = new TpMath();
        m3 = m.addLong(m1, m2, mf);
        try{
        		m5 = m.subDouble(m3, m4, mf);
        		Assert.fail();
        }catch(RuntimeException e){
        		m5 = m.subLong(m3, m4, mf);
        }
        //22 30 52 45 7
        long result = 7;
        Assert.assertEquals(result, m5.asLong());
	}
}
