package com.flytxt.parser.translator;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

public class TpDateUtilTest {

    private final TpDateUtil tpDu = new TpDateUtil();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public final void test2() {
        try {
            final byte[] des = null;
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

        } catch (final ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
