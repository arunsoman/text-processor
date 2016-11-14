package com.flytxt.tp.translator;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.marker.MarkerFactory;

public class TpDateUtilTest {

    private final TpDateUtil tpDu = new TpDateUtil();

    @Before
    public void setUp() throws Exception {
    }

    @Ignore
    @Test
    public final void test2() {
/*        try {
        	MarkerFactory mf = new MarkerFactory();
            final byte[] des = null;
            Marker str = mf.createMarker("ddMMyyyy HH:mm:ss");
            byte[] translate = tpDu.Formater(str).translate(src, null);
            System.out.println(new String(translate));

            str = mf.createMarker("MMddyyyy HH:mm:ss");
            translate = tpDu.Formater(str.toString()).translate(src, null);
            System.out.println(new String(translate));

            str = mf.createMarker("yyyyMMdd HH:mm:ss");
            translate = tpDu.Formater(str).translate(src, null);
            System.out.println(new String(translate));

            str = mf.createMarker("MMddyyyyHH:mm:ss");
            translate = tpDu.Formater(str).translate(src, null);
            System.out.println(new String(translate));

            str = mf.createMarker(" yyyyMMdd HH mm:ss ");
            translate = tpDu.Formater(str).translate(src, null);
            System.out.println(new String(translate));

        } catch (final ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 */   }

}
