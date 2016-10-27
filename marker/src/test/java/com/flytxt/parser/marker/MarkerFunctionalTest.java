package com.flytxt.parser.marker;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = 
		MarkerDefaultConfig.class)

public class MarkerFunctionalTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    
    @Autowired
    final MarkerFactory markerFactory = new MarkerFactory();
    @Test
    public void test1() {
        
        final String str = "1,,1,1,,45,30,2011-11-11T12:00:00-05:00,False,,,,,False,False,1,0,,,,,,,,,,,1,1";
        final byte[] d = str.getBytes();
        final Marker line = markerFactory.create(0, d.length);

        final byte[] t1 = TokenFactory.create(",Fa");
        final byte[] t2 = TokenFactory.create(",");

        final Marker m1 = line.splitAndGetMarker(d, t1, 4, markerFactory);
        logger.debug(m1.toString(d));
        final Marker m2 = m1.splitAndGetMarker(d, t2, 2, markerFactory);
        logger.debug(m2.toString(d));
        if (!m2.toString(d).equals("1")) {
            assertEquals(m2.toString(d), "1");
        }
    }

    @Test
    public void test2() {
        final String str = "a,{b|c},d";
        final byte[] d = str.getBytes();
        final Marker line = markerFactory.create(0, d.length);

        final byte[] t1 = TokenFactory.create(",{");
        final byte[] t2 = TokenFactory.create("|");

        final Marker m1 = line.splitAndGetMarker(d, t1, 2, markerFactory);
        logger.debug(m1.toString(d));
        final Marker m2 = m1.splitAndGetMarker(d, t2, 1, markerFactory);
        logger.debug(m2.toString(d));
        if (!m2.toString(d).equals("b")) {
            assertEquals(m2.toString(d), "b");
        }
    }

    @Test
    public void test3() {
        final String str = ",False,,,,,,,,F,,,";
        final byte[] d = str.getBytes();
        final Marker line = markerFactory.create(0, d.length);

        final byte[] t1 = TokenFactory.create(",F");

        final Marker m1 = line.splitAndGetMarker(d, t1, 2, markerFactory);
        logger.debug(m1.toString(d));
        // if (!m1.toString(d).equals("False")) {
        // assertEquals(m1.toString(d), "False");
        // }
    }

}
