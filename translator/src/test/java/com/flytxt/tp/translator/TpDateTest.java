package com.flytxt.tp.translator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.joda.time.DateTime;
import org.junit.Test;

import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.marker.Router;
import com.flytxt.tp.translator.tpdateutils.TpDateUtil;

public class TpDateTest extends TpAbsTest {

	TpDate tpDate = new TpDate();

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

	//@Test
	public final void testDifferenceInMillis() {
		
		DateTime testTime =  new DateTime();
		String d1 = dateToString(testTime);
		
		DateTime testTime2 =  new DateTime();
		String d2 = dateToString(testTime2);
		
		Marker m1 = getMarker(d1);
		Marker m2 = getMarker(d2);
		
		try {
			assertEquals(testTime.getMillis()-testTime2.getMillis(), tpDate.differenceInMillis(m1, m2, markerFactory));
		} catch (ParseException e) {
			fail(e.getMessage());
		}
		
	
	}

	@Test
	public void extractDateFromLine(){
		final String str = 
				//"1,22,1,1,,45,30,False$2011-11-11T12:00:00-05:00+FAlse*,,,-False^False,1,0,,,,,,,,,,,1,1";
				"1,22,1,1,,45,30,False$2011-11-11T12:00:00+FAlse*,,,-False^False,1,0,,,,,,,,,,,1,1";
        final Marker line = getMarker(str);
		Marker tmp = markerFactory.createMarker(null, 0, 0);
		Marker tmp1 = markerFactory.createMarker(null, 0, 0);
		line.splitAndGetMarkers("$".getBytes(), new Router(new int[]{1}), markerFactory, tmp);
		tmp.splitAndGetMarkers("+".getBytes(), new Router(new int[]{0}), markerFactory, tmp1);
		try {
			tmp1 = tpDate.convertDate(tmp1, markerFactory, "yyyy-mm-dd'T'HH:mm:ss");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void extractDateWithZFromLine(){
		final String str = 
				"1,22,1,1,,45,30,False$2011-11-11T12:00:00-05:00+FAlse*,,,-False^False,1,0,,,,,,,,,,,1,1";
        final Marker line = getMarker(str);
		Marker tmp = markerFactory.createMarker(null, 0, 0);
		Marker tmp1 = markerFactory.createMarker(null, 0, 0);
		line.splitAndGetMarkers("$".getBytes(), new Router(new int[]{1}), markerFactory, tmp);
		tmp.splitAndGetMarkers("+".getBytes(), new Router(new int[]{0}), markerFactory, tmp1);
		try {
			tmp1 = tpDate.convertDate(tmp1, markerFactory, "yyyy-mm-dd'T'HH:mm:ssX");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	@Test
	public final void testToLong() {
		
		DateTime testTime =  new DateTime();		
		String d1 = dateToString(testTime);
		try {
			TpDateUtil tpDateUtil = new TpDateUtil();			
			Marker m1 = getMarker(d1);
			assertEquals( tpDateUtil.parse(d1).getMillis(), tpDate.toLong(m1, markerFactory));
		} catch (ParseException e) {
			fail(e.getMessage());
		}	
	}
	
	@Test
	public void textConvertDate(){
		Marker tmp1 = markerFactory.createMarker(null, 0, 0);
		try {
			tmp1 = tpDate.convertDate(tmp1, markerFactory, "yyyy-mm-dd'T'HH:mm:ssX");
		} catch (ParseException e) {
			assertEquals(true, e.getMessage().contains("no data in marker to parse with format"));
		}
	}
	
	
	
	/**
	 * 
	 * @param dt
	 * @return
	 */
	private String dateToString(org.joda.time.DateTime dt) {
	
		org.joda.time.format.DateTimeFormatter fmt = org.joda.time.format.DateTimeFormat.forPattern( "ddMMyyyy HH:mm:ss.SZ");
		String dateAsstr = fmt.print(dt);
		return dateAsstr;
	}

	
	
}
