package com.flytxt.parser.marker;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Getter;
import lombok.Setter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = 
		MarkerDefaultConfig.class)

public class MarkerSplitTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@Autowired
	MarkerFactory markerFactory;

	@Test
	public void test1() {
		final String strb = "1,,1,1,,45,30,2011-11-11T12:00:00-05:00,False,,,,,False,False,1,0,,,,,,,,,,,1,1";
		final byte[] b = strb.getBytes();

		final String str = ",1,";
		final String splits[] = strb.split(str);
		markerFactory.setMaxListSize(splits.length);
		final Marker line = markerFactory.create(0, b.length);
		final byte[] token = TokenFactory.create(str);
		final FlyList<Marker> ms = line.splitAndGetMarkers(b, token, markerFactory);
		if (splits.length != ms.size()) {
			assertEquals(splits.length, ms.size());
		}
		int k = 0;
		for (int i = 1; i <= ms.size(); i++) {
			if (!splits[k].equals(ms.get(i).toString(b))) {
				assertEquals(splits[k], ms.get(i).toString(b));
			}
			k++;
		}
	}

	@Test
	public void test2() {
		final String strb = ",a,b,c,d";
		final byte[] b = strb.getBytes();

		final String str = ",";
		final String splits[] = strb.split(str);
		markerFactory.setMaxListSize(splits.length);
		final Marker line = markerFactory.create(0, b.length);
		final byte[] token = TokenFactory.create(str);
		final FlyList<Marker> ms = line.splitAndGetMarkers(b, token, markerFactory);
		if (splits.length != ms.size()) {
			assertEquals(splits.length, ms.size());
		}
		logger.debug("length:" + ms.size());
		int k = 0;
		for (int i = 1; i <= ms.size(); i++) {
			if (!splits[k].equals(ms.get(i).toString(b))) {
				assertEquals(splits[k], ms.get(i).toString(b));
			}
			k++;
		}
	}

	@Test
	public void test3() {
		final String strb = "a,b,c,d,";
		final byte[] b = strb.getBytes();

		final String str = ",";
		final String splits[] = strb.split(str);
		markerFactory.setMaxListSize(splits.length);
		final Marker line = markerFactory.create(0, b.length);
		final byte[] token = TokenFactory.create(str);
		final FlyList<Marker> ms = line.splitAndGetMarkers(b, token, markerFactory);
		if (splits.length != ms.size()) {
			assertEquals(splits.length, ms.size());
		}
		logger.debug("length:" + ms.size());
		int k = 0;
		for (int i = 1; i <= ms.size(); i++) {
			if (!splits[k].equals(ms.get(i).toString(b))) {
				assertEquals(splits[k], ms.get(i).toString(b));
			}
			k++;
		}
	}

	@Test
	public void test4() {
		final String strb = "a,,b,,c,,d,,";
		final String str = ",,";
		final String splits[] = strb.split(str);
		final byte[] b = strb.getBytes();
		markerFactory.setMaxListSize(splits.length);
		final Marker line = markerFactory.create(0, b.length);
		final byte[] token = TokenFactory.create(str);
		final FlyList<Marker> ms = line.splitAndGetMarkers(b, token, markerFactory);
		if (splits.length != ms.size()) {
			assertEquals(splits.length, ms.size());
		}
		logger.debug("length:" + ms.size());
		int k = 0;
		for (int i = 1; i <= ms.size(); i++) {
			if (!splits[k].equals(ms.get(i).toString(b))) {
				assertEquals(splits[k], ms.get(i).toString(b));
			}
			k++;
		}
	}

}
