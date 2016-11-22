package com.flytxt.tp.translator;

import java.text.ParseException;

import org.joda.time.DateTime;

import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.marker.MarkerFactory;
import com.flytxt.tp.translator.tpdateutils.TpDateUtil;

public class TpDate {

    public static String flyDateFormat = "ddMMyyyy HH:mm:ss";

    private static TpDateUtil tpDateUtil = new TpDateUtil();

    private static int[] yearArray = { 4, 5, 6, 7 };

    private static int[] dateArray = { 0, 1 };

    private static int[] monthArray = { 2, 3 };

    private static int[] time = { 9, 10, 12, 13, 15, 16 };

    public Marker convertDate(final Marker m, final MarkerFactory mf, final String format) throws ParseException {
    	if(m.length == 0){
    		throw new ParseException("no data in marker to parse with format" +format, 0);
    	}
    	byte[] translate = tpDateUtil.toDateBytes(m,format);
    	return mf.createMarker(translate, 0, translate.length);
    }

    public boolean after(final Marker m, final Marker m2) {
        final byte[] d1 = m.getData();
        final byte[] d2 = m2.getData();
        int b;
        int a = b = 1;
        for (final int i : yearArray) {
            a += d1[m.index + i];
            a *= 10;
            b += d2[m2.index + i];
            b *= 10;
        }
        if (a != b)
            return a > b;
            a = b = 1;
            for (final int i : monthArray) {
                a += d1[m.index + i];
                a *= 10;
                b += d2[m2.index + i];
                b *= 10;
            }
            if (a != b)
                return a > b;
                a = b = 1;
                for (final int i : dateArray) {
                    a += d1[m.index + i];
                    a *= 10;
                    b += d2[m2.index + i];
                    b *= 10;
                }
                if (a != b)
                    return a > b;
                    a = b = 1;
                    for (final int i : time) {
                        a += d1[m.index + i];
                        a *= 10;
                        b += d2[m2.index + i];
                        b *= 10;
                    }
                    return a > b;
    }

    public boolean before(final Marker m, final Marker m2) {
        return !after(m, m2);
    }

    public Marker differenceInMillis(final Marker m, final Marker m2, MarkerFactory mf) throws ParseException {
    	DateTime diff = tpDateUtil.parse(m.toString()).minus(tpDateUtil.parse(m2.toString()).getMillis());
    	return mf.createMarker(String.valueOf(diff.getMillis()));
   	}

    public long toLong(final Marker m, MarkerFactory mf) throws ParseException {
    	return tpDateUtil.parse(m.toString()).getMillis();
    }
}
