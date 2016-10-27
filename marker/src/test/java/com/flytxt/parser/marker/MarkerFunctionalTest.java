package com.flytxt.parser.marker;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = 
		MarkerDefaultConfig.class)

public class MarkerFunctionalTest {

	@Autowired
    final MarkerFactory markerFactory = new MarkerFactory();
    
    private CurrentObject currentObject;
    
    @Before
    public void init(){
    	currentObject = markerFactory.getCurrentObject();
    	currentObject.setFileName("TestFile");
    	currentObject.setFolderName("TestFolder");
    }
    
    public Marker getMarker(String str){
    	byte[] lineMarker = str.getBytes();
    	currentObject.setLineMarker(lineMarker);
    	currentObject.setCurrentLine(0, lineMarker.length);
    	Marker line = markerFactory.createMarker(null,0, lineMarker.length);
    	return line;
    }
    @Test
    public void test1() {        
        final String str = "1,,1,1,,45,30,2011-11-11T12:00:00-05:00,False,,,,,False,False,1,0,,,,,,,,,,,1,1";
        final Marker line = getMarker(str);
        final byte[] t1 = TokenFactory.create(",Fa");
        final byte[] t2 = TokenFactory.create(",");
        final Marker m1 = line.splitAndGetMarker(t1, 4, markerFactory);
        final Marker m2 = m1.splitAndGetMarker(t2, 2, markerFactory);
       
        if (!m2.toString().equals("1")) {
            assertEquals("1",m2.toString());
        }
    }

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

}
