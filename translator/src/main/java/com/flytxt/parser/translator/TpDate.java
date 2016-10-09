package com.flytxt.parser.translator;

import java.text.ParseException;
import java.util.HashMap;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;
import com.flytxt.parser.translator.TpDateUtil.Translator;

public class TpDate {

    public static String flyDateFormat = "ddMMyyyy HH:mm:ss";

    private static TpDateUtil tpDateUtil = new TpDateUtil();  

    private static int[] yearArray = { 4, 5, 6, 7 };

    private static int[] dateArray = { 0, 1 };

    private static int[] monthArray = { 2, 3 };

    private static int[] time = { 9, 10, 12, 13, 15, 16 };
    private static HashMap<String, Translator> planMap= new HashMap<String, Translator>();
    
    public Marker convertDate(final byte[] data, final Marker m, final MarkerFactory mf, final String format) throws ParseException {
        Translator translator = planMap.get(format);
        if(translator == null){
        	translator = tpDateUtil.Formater(format);
        	planMap.put(format, translator);
        }
        byte[] translate = translator.translate(data, null);
        return mf.createImmutable(translate, 0, translate.length);
    }

    public boolean after(final byte[] data, final Marker m, final byte[] data2, final Marker m2) {
        final byte[] d1 = (m.getData() != null) ? m.getData() : data;
        final byte[] d2 = (m2.getData() != null) ? m2.getData() : data2;
        int b;
        int a = b = 1;
        for (final int i : yearArray) {
            a += d1[m.index + i];
            a *= 10;
            b += d2[m2.index + i];
            b *= 10;
        }
        if (a != b) {
            return a > b;
        }
        a = b = 1;
        for (final int i : monthArray) {
            a += d1[m.index + i];
            a *= 10;
            b += d2[m2.index + i];
            b *= 10;
        }
        if (a != b) {
            return a > b;
        }
        a = b = 1;
        for (final int i : dateArray) {
            a += d1[m.index + i];
            a *= 10;
            b += d2[m2.index + i];
            b *= 10;
        }
        if (a != b) {
            return a > b;
        }
        a = b = 1;
        for (final int i : time) {
            a += d1[m.index + i];
            a *= 10;
            b += d2[m2.index + i];
            b *= 10;
        }
        return a > b;
    }

    public boolean before(final byte[] data, final Marker m, final byte[] data2, final Marker m2) {
        final byte[] d1 = (m.getData() != null) ? m.getData() : data;
        final byte[] d2 = (m2.getData() != null) ? m2.getData() : data2;
        return !after(d1, m, d2, m2);
    }

    public long differenceInMillis(final byte[] data, final Marker m, final byte[] data2, final Marker m2) throws ParseException {
        final byte[] d1 = (m.getData() != null) ? m.getData() : data;
        final byte[] d2 = (m2.getData() != null) ? m2.getData() : data2;
        return tpDateUtil.parse(m.toString(d1)).getTime() - tpDateUtil.parse(m2.toString(d2)).getTime();
    }
}
