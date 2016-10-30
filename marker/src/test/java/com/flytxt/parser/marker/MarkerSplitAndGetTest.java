package com.flytxt.parser.marker;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MarkerDefaultConfig.class)

public class MarkerSplitAndGetTest {

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
    public void test() {
        final String strb = "a,bb,c,d";
        final Marker line = getMarker(strb);
        final int get = 1;
        final int javaIndex = get - 1;

        final String str = ",";
        final byte[] token = TokenFactory.create(str);
        final Marker ms = line.splitAndGetMarker(token, get, markerFactory);
        final String splits[] = strb.split(str);
        // for user index starts @ 1
        if (!splits[javaIndex].equals(ms.toString())) {
            assertEquals(splits[javaIndex], ms.toString());
        }
    }

    @Test
    public void test1() {
        final String strb = "c,d";
        final Marker line = getMarker(strb);
        final String str = ",";
        final String splits[] = strb.split(str);
        final int get = splits.length;
        final int javaIndex = get - 1;
        final byte[] token = TokenFactory.create(str);
        markerFactory.setMaxListSize(splits.length);
        final Marker ms = line.splitAndGetMarker(token, get, markerFactory);

        // for user index starts @ 1
        if (!splits[javaIndex].equals(ms.toString())) {
            assertEquals(splits[javaIndex], ms.toString());
        }
    }

    @Test
    public void test2() {
        final String strb = ",c,d";
        final Marker line = getMarker(strb);
        final String str = ",";
        final String splits[] = strb.split(str);
        final int get = 1;
        final int javaIndex = get - 1;

        final byte[] token = TokenFactory.create(str);
        final Marker ms = line.splitAndGetMarker( token, get, markerFactory);

        // for user index starts @ 1
        if (!splits[javaIndex].equals(ms.toString())) {
            assertEquals(splits[javaIndex], ms.toString());
        }
    }

    @Test
    public void test3() {
        final String strb = ",c,d,";
        final Marker line = getMarker(strb);
        final String str = ",";
        final String splits[] = strb.split(str);
        final int get = splits.length;
        final int javaIndex = get - 1;

        final byte[] token = TokenFactory.create(str);
        final Marker ms = line.splitAndGetMarker(token, get, markerFactory);

        // for user index starts @ 1
        if (!splits[javaIndex].equals(ms.toString())) {
            assertEquals(splits[javaIndex], ms.toString());
        }
    }
}
