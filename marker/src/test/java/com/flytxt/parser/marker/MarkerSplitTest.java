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

public class MarkerSplitTest {
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
    
	@Autowired
	MarkerFactory markerFactory;

	@Test
	public void test1() {
		final String strb = "1,,1,1,,45,30,2011-11-11T12:00:00-05:00,False,,,,,False,False,1,0,,,,,,,,,,,1,1";
		final Marker line = getMarker(strb);

		final String str = ",1,";
		final String splits[] = strb.split(str);
		markerFactory.setMaxListSize(splits.length);
		
		final byte[] token = TokenFactory.create(str);
		final FlyList<Marker> ms = line.splitAndGetMarkers(token, markerFactory);
		if (splits.length != ms.size()) {
			assertEquals(splits.length, ms.size());
		}
		int k = 0;
		for (int i = 1; i <= ms.size(); i++) {
			if (!splits[k].equals(ms.get(i).toString())) {
				assertEquals(splits[k], ms.get(i).toString());
			}
			k++;
		}
	}

	@Test
	public void test2() {
		final String strb = ",a,b,c,d";
		final Marker line = getMarker(strb);

		final String str = ",";
		final String splits[] = strb.split(str);
		markerFactory.setMaxListSize(splits.length);
		
		final byte[] token = TokenFactory.create(str);
		final FlyList<Marker> ms = line.splitAndGetMarkers(token, markerFactory);
		if (splits.length != ms.size()) {
			assertEquals(splits.length, ms.size());
		}
		int k = 0;
		for (int i = 1; i <= ms.size(); i++) {
			if (!splits[k].equals(ms.get(i).toString())) {
				assertEquals(splits[k], ms.get(i).toString());
			}
			k++;
		}
	}

	@Test
	public void test3() {
		final String strb = "a,b,c,d,";
		final Marker line = getMarker(strb);

		final String str = ",";
		final String splits[] = strb.split(str);
		markerFactory.setMaxListSize(splits.length);
		
		final byte[] token = TokenFactory.create(str);
		final FlyList<Marker> ms = line.splitAndGetMarkers(token, markerFactory);
		if (splits.length != ms.size()) {
			assertEquals(splits.length, ms.size());
		}
		int k = 0;
		for (int i = 1; i <= ms.size(); i++) {
			if (!splits[k].equals(ms.get(i).toString())) {
				assertEquals(splits[k], ms.get(i).toString());
			}
			k++;
		}
	}

	@Test
	public void test4() {
		final String strb = "a,,b,,c,,d,,";
		final String str = ",,";
		final String splits[] = strb.split(str);
		final Marker line = getMarker(strb);
		markerFactory.setMaxListSize(splits.length);
		
		final byte[] token = TokenFactory.create(str);
		final FlyList<Marker> ms = line.splitAndGetMarkers(token, markerFactory);
		if (splits.length != ms.size()) {
			assertEquals(splits.length, ms.size());
		}
		int k = 0;
		for (int i = 1; i <= ms.size(); i++) {
			if (!splits[k].equals(ms.get(i).toString())) {
				assertEquals(splits[k], ms.get(i).toString());
			}
			k++;
		}
	}

}
