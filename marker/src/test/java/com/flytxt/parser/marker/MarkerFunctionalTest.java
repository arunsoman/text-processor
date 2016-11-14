package com.flytxt.parser.marker;

import org.junit.Test;

import com.flytxt.tp.marker.ConstantMarker;
import com.flytxt.tp.marker.Marker;

public class MarkerFunctionalTest extends TestConstruct implements ConstantMarker{
	final String str = 
"1,22,1,1,,45,30,False$2011-11-11T12:00:00-05:00+FAlse*,,,-False^False,1,0,,,,,,,,,,,1,1";
    
	@Test
    public void testFalseAsToken() {        
        final Marker line = getLineMarker(str);
        Marker m1 = markerFactory.createMarker(null, 0, 0);
        Marker m2 = markerFactory.createMarker(null, 0,0);
        Marker m3 = markerFactory.createMarker(null, 0,0);
        int [] indices = new int[]{1,2};
        line.splitAndGetMarkers("False".getBytes(),indices , markerFactory, m1, m2);
        validate("False", indices, str, m1,m2);
    }
	
	@Test
    public void testFalse() {        
        final Marker line = getLineMarker(str);
        Marker m1 = markerFactory.createMarker(null, 0, 0);
        Marker m2 = markerFactory.createMarker(null, 0,0);
        Marker m3 = markerFactory.createMarker(null, 0,0);
        int [] indices = new int[]{0,2};
        line.splitAndGetMarkers("False".getBytes(),indices , markerFactory, m1, m2);
        validate("False", indices, str, m1,m2);
    }

	@Test
    public void testWith0() {        
        final Marker line = getLineMarker(str);
        Marker m1 = markerFactory.createMarker(null, 0, 0);
        int [] indices = new int[]{0};
        line.splitAndGetMarkers(",".getBytes(),indices , markerFactory, m1);
        validate(",", indices, str, m1);
    }
	
	@Test
    public void test1() {        
        final Marker line = getLineMarker(str);
        Marker m1 = markerFactory.createMarker(null, 0, 0);
        Marker m2 = markerFactory.createMarker(null, 0,0);
        Marker m3 = markerFactory.createMarker(null, 0,0);
        int [] indices = new int[]{1,5};
        line.splitAndGetMarkers(",".getBytes(),indices , markerFactory, m1, m2);
        validate(",", indices, str, m1,m2);
    }
    /**
     * this is not supported yet, need new data structure
     */
    //@Test
    public void testSameMarker2() {        
        final Marker line = getLineMarker(str);
        Marker m1 = markerFactory.createMarker(null, 0, 0);
        Marker m2 = markerFactory.createMarker(null, 0,0);
        int [] indices = new int[]{1,1};
        line.splitAndGetMarkers(",".getBytes(),indices , markerFactory, m1, m2);
        validate(",", indices, str, m1,m2);
    }

    @Test
    public void test3() {        
        final Marker line = getLineMarker(str);
        Marker m1 = markerFactory.createMarker(null, 0, 0);
        Marker m2 = markerFactory.createMarker(null, 0,0);
        Marker m3 = markerFactory.createMarker(null, 0,0);
        int [] indices = new int[]{4,1};
        line.splitAndGetMarkers(",".getBytes(),indices , markerFactory, m1, m2);
        validate(",", indices, str, m1,m2);
    }
    
    @Test
    public void test4() {        
        final Marker line = getLineMarker(str);
        Marker m1 = markerFactory.createMarker(null, 0, 0);
        Marker m2 = markerFactory.createMarker(null, 0,0);
        Marker m3 = markerFactory.createMarker(null, 0,0);
        int [] indices = new int[]{4,1, 9};
        line.splitAndGetMarkers(",".getBytes(),indices , markerFactory, m1, m2,m3);
        validate(",", indices, str, m1,m2, m3);
    }
    
    @Test
    public void test5() {        
        final Marker line = getLineMarker(str);
        Marker m1 = markerFactory.createMarker(null, 0, 0);
        Marker m2 = markerFactory.createMarker(null, 0,0);
        Marker m3 = markerFactory.createMarker(null, 0,0);
        int [] indices = new int[]{7};
        line.splitAndGetMarkers(",".getBytes(),indices , markerFactory, m1);
        validate(",", indices, str, m1);
        indices = new int[]{2,1};
        m1.splitAndGetMarkers("-".getBytes(),indices , markerFactory, m2,m3);
        validate("-", indices, m1.toString(), m2, m3);
    }

    @Test
    public void testArrayOutOfBound1() {        
        final Marker line = getLineMarker(str);
        Marker m1 = markerFactory.createMarker(null, 0, 0);
        Marker m2 = markerFactory.createMarker(null, 0,0);
        Marker m3 = markerFactory.createMarker(null, 0,0);
        int [] indices = new int[]{7};
        line.splitAndGetMarkers(",".getBytes(),indices , markerFactory, m1);
        validate(",", indices, str, m1);
        indices = new int[]{0,19};
        m1.splitAndGetMarkers("-".getBytes(),indices , markerFactory, m2,m3);
        validate("-", indices, m1.toString(), m2, m3);
    }

}
