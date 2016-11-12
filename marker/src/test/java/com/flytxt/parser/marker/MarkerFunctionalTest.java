package com.flytxt.parser.marker;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.flytxt.tp.marker.ConstantMarker;
import com.flytxt.tp.marker.Marker;

public class MarkerFunctionalTest extends TestConstruct implements ConstantMarker{
    @Test
    public void test1() {        
        final String str = "1,,1,1,,45,30,2011-11-11T12:00:00-05:00,False,,,,,False,False,1,0,,,,,,,,,,,1,1";
        final Marker line = getLineMarker(str);
        Marker m1 = mnull;
        Marker m2 = mnull;
        Marker m3 = mnull;
        int [] indices = new int[]{1,4};
        line.splitAndGetMarkers(",".getBytes(),indices , markerFactory, m1, m2);
        validate(",", indices, str, m1,m2);

    }
/*s
    @Test
    public void test2() {
        final String str = "a,{b|c},d";
        final Marker line = getMarker(str);
        final byte[] t1 = TokenFactory.create(",{");
        final byte[] t2 = TokenFactory.create("|");
        final Marker m1 = line.splitAndGetMarker( t1, 2, markerFactory);
        final Marker m2 = m1.splitAndGetMarker( t2, 1, markerFactory);
        if (!m2.toString().equals("b")) {
            assertEquals("b",m2.toString());
        }
    }

    @Test
    public void test3() {
        final String str = ",False,,,,,,,,F,,,";
        final Marker line = getMarker(str);
        final byte[] t1 = TokenFactory.create(",F");
        final Marker m1 = line.splitAndGetMarker( t1, 2, markerFactory);
        
        // if (!m1.toString(d).equals("False")) {
        // assertEquals(m1.toString(d), "False");
        // }
    }
*/
}
