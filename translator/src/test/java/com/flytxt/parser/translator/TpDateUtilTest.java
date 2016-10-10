package com.flytxt.parser.translator;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import com.flytxt.parser.translator.TpDateUtil.CoOccur;

public class TpDateUtilTest {
	private TpDateUtil tpDu = new TpDateUtil();
	@Before
	public void setUp() throws Exception {
	}
   
	@Test
	public final void test2() {
		try {
			byte[] des = null;
			String str = "ddMMyyyy HH:mm:ss";
			byte[] src = str.getBytes();
			byte[] translate = tpDu.Formater(str).translate(src, null);
			System.out.println(new String(translate));

			str = "MMddyyyy HH:mm:ss";
			src = str.getBytes();
			translate = tpDu.Formater(str).translate(src, null);
			System.out.println(new String(translate));
			
			str = "yyyyMMdd HH:mm:ss";
			src = str.getBytes();
			translate = tpDu.Formater(str).translate(src, null);
			System.out.println(new String(translate));
			
			str = "MMddyyyyHH:mm:ss";
			src = str.getBytes();
			translate = tpDu.Formater(str).translate(src, null);
			System.out.println(new String(translate));
			
			str = " yyyyMMdd HH mm:ss ";
			src = str.getBytes();
			translate = tpDu.Formater(str).translate(src, null);
			System.out.println(new String(translate));

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
