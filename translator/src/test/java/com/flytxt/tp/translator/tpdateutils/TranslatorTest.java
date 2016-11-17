package com.flytxt.tp.translator.tpdateutils;

import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
	public void init(){
		list.add("yyyy.MM.dd G 'at' HH:mm:ss z");//2001.07.04 AD at 12:08:56 PDT
		list.add("EEE, MMM d, ''yy");//Wed, Jul 4, '01
		list.add("h:mm a");//12:08 PM
		list.add("hh 'o''clock' a, zzzz");//12 o'clock PM, Pacific Daylight Time
		list.add("K:mm a, z");//0:08 PM, PDT
		list.add("yyyyy.MMMMM.dd GGG hh:mm aaa");//02001.July.04 AD 12:08 PM
		list.add("EEE, d MMM yyyy HH:mm:ss Z");//Wed, 4 Jul 2001 12:08:56 -0700
		list.add("yyMMddHHmmssZ");//010704120856-0700
		list.add("yyyy-MM-dd'T'HH:mm:ss.SSSZ");//2001-07-04T12:08:56.235-0700
		list.add("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");//2001-07-04T12:08:56.235-07:00
		list.add("YYYY-'W'ww-u");//2001-W27-3
	}
	
	@Test
	public void testTranslatorIntArrayArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testTranslatorSimpleDateFormat() {
		for(int i =0;i< list.size();i++){
			String fmt = list.get(i);
			System.out.println(fmt);
		Translator t = new Translator(fmt);
		ZonedDateTime now = LocalDateTime.now().atZone(ZoneId.systemDefault());
		String str = now.toString();
		DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern(fmt);
		String newstring = now.format(ofPattern);
		String expected =LocalDateTime.parse(newstring,ofPattern)
				.format(DateTimeFormatter.ofPattern(Translator.flyDateFormat));
		Marker m = mf.createMarker(newstring);
		try {
			byte[] result =t.translate(m, null);
			if(!expected.equals(new String(result))){
				Assert.assertEquals(expected, new String(result));
				
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}

	@Test
	public void testTranslate() {
		fail("Not yet implemented");
	}

	@Test
	public void testParse() {
		fail("Not yet implemented");
	}

}
