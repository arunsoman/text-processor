package com.flytxt.tp.translator.tpdateutils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
	private MarkerFactory mf = new MarkerFactory();

	@Before
	public void init() {

	}

	class Sample{
		String input;
		String fmt;
		public DateTime date;
	}
	String[] goodFmts = {
//			"yyyy.MM.ddHH",
			"yyyy.MM.ddHH:mm:ssSZ" 
			," MM.yyyy.ddHH:mm:ssSZ"
	};

	String[] badFmts = {
		//	"Zyyyy.MM.ddHH:mm:ssS " ,
			" yyyyy.MMMMM.dd GGG hh:mm aaa ",
			"EEE, d MMM yyyy HH:mm:ss Z",

			"yyMMddHHmmssZ",

			"yyyy-MM-dd'T'HH:mm:ss.SSSZ"

	};
	private List<Sample>genSamples(String[] fmts){
		DateTime dt = new DateTime();
		List<Sample> list = new ArrayList<Sample>(fmts.length);
		int i = 0;
		Sample sample;
		for (String fmt : fmts) {
			sample = new Sample();
			sample.input= toString(dt, fmt);
			sample.fmt = fmts[i++];
			sample.date = dt;
			list.add(sample);
		}
		return list;
	}
	private byte[] convertToFlyFmt(String fmtStr, String dateAsstr) throws ParseException {
		CoOccur coOccur = new CoOccur(fmtStr);
		Translator t = new Translator(coOccur.toPlan());
		Marker m = mf.createMarker(dateAsstr);
		byte[] result = t.translate(m, null);
		return result;
	}
	
	private byte[] convertToFlyFmtSDF(String fmtStr, String dateAsstr) throws ParseException {
		Translator t = new Translator(fmtStr);
		Marker m = mf.createMarker(dateAsstr);
		byte[] result = t.translate(m, null);
		return result;
	}

	private String toString(DateTime dt, String fmtStr) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern(fmtStr);
		String dateAsstr = fmt.print(dt);
		return dateAsstr;
	}

	@Test
	public void testTranslation() {
		List<Sample> list = genSamples(goodFmts);
		for (int i = 0; i < list.size(); i++) {
			String input = list.get(i).input;
			String expected = toString(list.get(i).date, Translator.flyDateFormat);
			try {
				
				byte[] result = convertToFlyFmt(list.get(i).fmt, input);
//				System.out.println(list.get(i).fmt);
//				System.out.println("input     :" + input);
				System.out.println("expected  :" + expected);
				System.out.println("result    :" + new String(result));
			if (!expected.equals(new String(result))) {
					Assert.assertEquals(expected, new String(result));
				}
			} catch (ParseException e) {
				System.out.println(e.getMessage()); 
				System.out.println(list.get(i).input+" : "+list.get(i).fmt);
				Assert.fail();
			}
		}
	}

	@Test
	public void testbadTranslation() {
		List<Sample> list = genSamples(badFmts);
		for (int i = 0; i < list.size(); i++) {
			String input = list.get(i).input;
	//		String expected = toString(list.get(i).date, Translator.flyDateFormat);
			try {

				byte[] result = convertToFlyFmt(list.get(i).fmt, input);
				Assert.fail();
			} catch (ParseException e) {
				
			}
		}
	}
	@Test
	public void testbadSDF() {
		List<Sample> list = genSamples(badFmts);
		
		DateTimeFormatter sfmt = DateTimeFormat.forPattern(Translator.flyDateFormat);
		for (int i = 0; i < list.size(); i++) {
			String input = list.get(i).input;
			DateTimeFormatter sfmt1 = DateTimeFormat.forPattern(list.get(i).fmt);
			String expected = sfmt.print(sfmt1.parseDateTime(input)).toString();
			
			
			try {
				byte[] result = convertToFlyFmtSDF(list.get(i).fmt, input);
				if (!expected.equals(new String(result))) {
					Assert.assertEquals(expected, new String(result));
				}
			} catch (ParseException e) {
				Assert.fail();
			}
		}
	}
	
	@Test
	public void testGoodSDF() {
		List<Sample> list = genSamples(goodFmts);
		for (int i = 0; i < list.size(); i++) {
			String input = list.get(i).input;
			String expected = toString(list.get(i).date, Translator.flyDateFormat);
			try {

				byte[] result = convertToFlyFmtSDF(list.get(i).fmt, input);
				if (!expected.equals(new String(result))) {
					Assert.assertEquals(expected, new String(result));
				}
			} catch (ParseException e) {
				Assert.fail();
			}
		}
	}
}
