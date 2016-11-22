package com.flytxt.tp.translator.tpdateutils;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.joda.time.DateTime;
import org.junit.Test;


public class TpDateUtilTest {

	TpDateUtil  dateUtil = new TpDateUtil();
	
	@Test
	public void testParse() {
		DateTime testTime =  new DateTime();
		try {
			DateTime dateTime = dateUtil.parse(dateToString(testTime));
			assertEquals(testTime.getDayOfMonth(), dateTime.getDayOfMonth());
		} catch (ParseException e) {
			e.printStackTrace();
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
