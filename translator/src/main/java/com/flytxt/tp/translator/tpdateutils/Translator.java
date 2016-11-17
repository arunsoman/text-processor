package com.flytxt.tp.translator.tpdateutils;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.flytxt.tp.marker.Marker;

class Translator{
    public static final String flyDateFormat = "ddMMyyyy HH:mm:ss.SZ";
     static final int flyDateFormatSize = flyDateFormat.length();
    public static final DateTimeFormatter sdf = DateTimeFormatter.ofPattern(flyDateFormat);

	private int[][]plan;
    private DateTimeFormatter srcFmt;
    
    Translator(int[][] plan) {
        super();
        this.plan = plan;
    }
    
    Translator(String srcFmt) {
        super();
        this.srcFmt = DateTimeFormatter.ofPattern(srcFmt);
    }
    public byte[] translate(Marker src, byte[] des) throws ParseException{
        return (srcFmt == null)?convert(src, des, plan):convert(src, des);
    }
    public static LocalDateTime parse(String dateStr) throws ParseException{
    	return LocalDateTime.parse(dateStr,sdf);
    }
    private byte[] convert(Marker src, byte[] des) throws ParseException{
    	return LocalDateTime.parse(src.toString(),srcFmt).format(sdf).getBytes();
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
