package com.flytxt.tp.translator.tpdateutils;

import java.text.ParseException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.flytxt.tp.marker.Marker;

class Translator{
    public static final String flyDateFormat = "ddMMyyyy HH:mm:ss.SZ";
     static final int flyDateFormatSize = flyDateFormat.length();

	private int[][]plan;
    private static DateTimeFormatter flyFmt = DateTimeFormat.forPattern(flyDateFormat);
    private DateTimeFormatter srcFmt;
    
    Translator(int[][] plan) {
        super();
        this.plan = plan;
    }
    
    Translator(String fmt) {
        super();
        this.srcFmt  = DateTimeFormat.forPattern(fmt);
    }
    public byte[] translate(Marker src, byte[] des) throws ParseException{
        return (srcFmt == null)?convert(src, des, plan):convert(src, des);
    }
    public static DateTime parse(String dateStr) throws ParseException{
    	 return flyFmt.parseDateTime(dateStr);
    }
    private byte[] convert(Marker src, byte[] des) throws ParseException{
    	String dateStr = src.toString();
		DateTime parseDateTime = srcFmt.parseDateTime(dateStr);
		return flyFmt.print(parseDateTime).getBytes();
    }
    private byte[] convert(Marker srcM, byte[] des,int[][]plan){
        if(des == null|| des.length != flyDateFormatSize)
            des = new byte[flyDateFormatSize];
        byte[] src = new byte[srcM.length];
        System.arraycopy(srcM.getData(), srcM.index, src, 0, srcM.length);
        for(int i=0; i < 6; i++)
            System.arraycopy(src, plan[i][0], des, plan[i][1], plan[i][2]);
        return des;
    }
}
