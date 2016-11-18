package com.flytxt.tp.translator.tpdateutils;

import java.text.ParseException;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.marker.MarkerFactory;

//@Ignore
public class TranslatorTest {
	private ArrayList<String> list = new ArrayList<>();
	private MarkerFactory mf = new MarkerFactory();

	@Before
	public void init() {
		list.add("yyyy.MM.ddHH:mm:ss ");// 2001.07.04 AD at 12:08:56 PDT
		list.add("yyyyy.MMMMM.dd GGG hh:mm aaa");// 02001.July.04 AD 12:08 PM
		list.add("EEE, d MMM yyyy HH:mm:ss Z");// Wed, 4 Jul 2001 12:08:56 -0700
		list.add("yyMMddHHmmssZ");// 010704120856-0700
		list.add("yyyy-MM-dd'T'HH:mm:ss.SSSZ");// 2001-07-04T12:08:56.235-0700
	}

	@Test
	public void t() {
		DateTime dt = new DateTime();
		DateTimeFormatter flyfmt = DateTimeFormat.forPattern(Translator.flyDateFormat);
		String dateAsFlystr = flyfmt.print(dt);
		System.out.println("\t" + dateAsFlystr);
		for (int i = 0; i < list.size(); i++) {
			String fmtStr = list.get(i);
			System.out.println(fmtStr);
			try {
				CoOccur coOccur = new CoOccur(fmtStr);
				Translator t = new Translator(coOccur.toPlan());
				DateTimeFormatter fmt = DateTimeFormat.forPattern(fmtStr);
				String dateAsstr = fmt.print(dt);
				System.out.println("\t"+dateAsstr);
				Marker m = mf.createMarker(dateAsstr);
				try {
					byte[] result = t.translate(m, null);
					System.out.println("\t\t"+ new String(result));
					if (!dateAsFlystr.equals(new String(result))) {
						Assert.assertEquals(dateAsFlystr, new String(result));
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (ParseException e) {
				System.out.println(e.getMessage());
				System.out.println(fmtStr);
			}
		}
	}

	public void testTranslatorSimpleDateFormat() {
		DateTime dt = new DateTime();
		DateTimeFormatter flyfmt = DateTimeFormat.forPattern(Translator.flyDateFormat);
		String dateAsFlystr = flyfmt.print(dt);
		System.out.println("\t" + dateAsFlystr);
		for (int i = 0; i < list.size(); i++) {
			String fmtStr = list.get(i);
			System.out.println(fmtStr);
			Translator t = new Translator(fmtStr);
			DateTimeFormatter fmt = DateTimeFormat.forPattern(fmtStr);
			String dateAsstr = fmt.print(dt);
			System.out.println("\t"+dateAsstr);
			Marker m = mf.createMarker(dateAsstr);
			try {
				byte[] result = t.translate(m, null);
				System.out.println("\t\t"+ new String(result));
				if (!dateAsFlystr.equals(new String(result))) {
					// Assert.assertEquals(dateAsFlystr, new String(result));
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}