package com.flytxt.parser.lookup;

import org.junit.Test;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;

public class MatchTest {

    @Test
    public void basicTest() {
    	final String[][] data = { 
        		{ "HelloWorld", "World" }, 
        		{ "Wallnut", "fruit" },
        		{ "HelloSmallWorld", "SmallWorld" },
        		};
        final MatchKey<Marker> object = new MatchKey();
        final MarkerFactory mf = new MarkerFactory();
        mf.setMaxListSize(100);

        for (final String[] datum : data) {
            object.load(datum[0].getBytes(), mf.createImmutable(datum[1].getBytes(), 0, datum[1].getBytes().length));
        }
        Marker marker = object.get("lln".getBytes());
        System.out.println((marker == null)? "null":marker.toString(marker.getData()));
        
        marker = object.get("Hell".getBytes());
        System.out.println(marker.toString(marker.getData()));
    }
}
