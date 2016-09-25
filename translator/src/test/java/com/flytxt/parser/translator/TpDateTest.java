package com.flytxt.parser.translator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;

import com.flytxt.parser.marker.Marker;

public class TpDateTest {

	TpDate tpDate = new TpDate();

	@Before
	public void setUp() throws Exception {
	}

	private Marker getMarker(String str) {
		Marker mocker = new Marker();
		mocker.index = 0;
		mocker.length = str.length();
		return mocker;
	}

	@Test
	public final void testConvertDate() {
		//fail("Not yet implemented");
	}

	@Test
	public final void testAfter() {
		SimpleDateFormat sdf = new SimpleDateFormat("ddmmyyy HH:mm:ss");
		String d1 = "01022012 12:00:00";
		String d2 = "01022013 12:00:00";
		byte[] d1b = d1.getBytes();
		byte[] d2b = d2.getBytes();
		Marker m1 = getMarker(d1);
		Marker m2 = getMarker(d2);
		try {
			assertEquals(sdf.parse(d1).after(sdf.parse(d2)), tpDate.after(d1b, m1, d2b, m2));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public final void testBefore() {
		SimpleDateFormat sdf = new SimpleDateFormat("ddmmyyy HH:mm:ss");
		String d1 = "01022012 12:00:00";
		String d2 = "01022013 12:00:00";
		byte[] d1b = d1.getBytes();
		byte[] d2b = d2.getBytes();
		Marker m1 = getMarker(d1);
		Marker m2 = getMarker(d2);
		try {
			assertEquals(sdf.parse(d1).before(sdf.parse(d2)), tpDate.before(d1b, m1, d2b, m2));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public final void testDifferenceInMillis() {
		//fail("Not yet implemented");
	}

}
