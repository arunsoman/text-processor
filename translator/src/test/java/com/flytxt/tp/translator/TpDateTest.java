package com.flytxt.tp.translator;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.translator.TpDate;

public class TpDateTest extends TpAbsTest {

	TpDate tpDate = new TpDate();

	@Test
	public final void testConvertDate() {
		//fail("Not yet implemented");
	}

	@Test
	public final void testAfter() {
		SimpleDateFormat sdf = new SimpleDateFormat("ddmmyyy HH:mm:ss");
		String d1 = "01022012 12:00:00";
		String d2 = "01022013 12:00:00";
		Marker m1 = getMarker(d1);
		Marker m2 = getMarker(d2);
		try {
			assertEquals(sdf.parse(d1).after(sdf.parse(d2)), tpDate.after(m1,  m2));
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
		Marker m1 = getMarker(d1);
		Marker m2 = getMarker(d2);
		try {
			assertEquals(sdf.parse(d1).before(sdf.parse(d2)), tpDate.before( m1,  m2));
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
