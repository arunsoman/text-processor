package com.flytxt.parser.marker;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MarkerDefaultConfig.class)

public class MarkerSplitAndGetTest {

	@Autowired
    private MarkerFactory markerFactory;

    @Test
    public void test() {
        final String strb = "a,bb,c,d";
        final byte[] b = strb.getBytes();
        final int get = 1;
        final int javaIndex = get - 1;

        final String str = ",";
        final Marker line = markerFactory.create(0, b.length);
        final byte[] token = TokenFactory.create(str);
        final Marker ms = line.splitAndGetMarker(b, token, get, markerFactory);
        final String splits[] = strb.split(str);
        // for user index starts @ 1
        if (!splits[javaIndex].equals(ms.toString(b))) {
            assertEquals(splits[javaIndex], ms.toString(b));
        }
    }

    @Test
    public void test1() {
        final String strb = "c,d";
        final byte[] b = strb.getBytes();
        final String str = ",";
        final String splits[] = strb.split(str);
        final int get = splits.length;
        final int javaIndex = get - 1;

        final Marker line = markerFactory.create(0, b.length);
        final byte[] token = TokenFactory.create(str);
        markerFactory.setMaxListSize(splits.length);
        final Marker ms = line.splitAndGetMarker(b, token, get, markerFactory);

        // for user index starts @ 1
        if (!splits[javaIndex].equals(ms.toString(b))) {
            assertEquals(splits[javaIndex], ms.toString(b));
        }
    }

    @Test
    public void test2() {
        final String strb = ",c,d";
        final byte[] b = strb.getBytes();
        final String str = ",";
        final String splits[] = strb.split(str);
        final int get = 1;
        final int javaIndex = get - 1;

        final Marker line = markerFactory.create(0, b.length);
        final byte[] token = TokenFactory.create(str);
        final Marker ms = line.splitAndGetMarker(b, token, get, markerFactory);

        // for user index starts @ 1
        if (!splits[javaIndex].equals(ms.toString(b))) {
            assertEquals(splits[javaIndex], ms.toString(b));
        }
    }

    @Test
    public void test3() {
        final String strb = ",c,d,";
        final byte[] b = strb.getBytes();
        final String str = ",";
        final String splits[] = strb.split(str);
        final int get = splits.length;
        final int javaIndex = get - 1;

        final Marker line = markerFactory.create(0, b.length);
        final byte[] token = TokenFactory.create(str);
        final Marker ms = line.splitAndGetMarker(b, token, get, markerFactory);

        // for user index starts @ 1
        if (!splits[javaIndex].equals(ms.toString(b))) {
            assertEquals(splits[javaIndex], ms.toString(b));
        }
    }
}
