package com.flytxt.tp.translator.tpdateutils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.flytxt.tp.marker.Marker;

class Translator{
    public static final String flyDateFormat = "ddMMyyyy HH:mm:ss.S";
    private static final int flyDateFormatSize = flyDateFormat.length();
    private static final SimpleDateFormat sdf = new SimpleDateFormat(flyDateFormat);

	int[][]plan;
    SimpleDateFormat srcFmt;
    
    Translator(int[][] plan) {
        super();
        this.plan = plan;
    }
    
    Translator(SimpleDateFormat srcFmt) {
        super();
        this.srcFmt = srcFmt;
    }
    public byte[] translate(Marker src, byte[] des) throws ParseException{
        return (srcFmt == null)?translate(src, des, plan):translate(src, des, srcFmt);
    }
    public static Date parse(String dateStr) throws ParseException{
    	return sdf.parse(dateStr);
    }
    private byte[] translate(Marker src, byte[] des,SimpleDateFormat srcfmt) throws ParseException{
        return sdf.format(srcfmt.parse(new String(src.getData(), src.index, src.length))).getBytes();
    }
    private byte[] translate(Marker srcM, byte[] des,int[][]plan){
        if(des == null|| des.length != flyDateFormatSize)
            des = new byte[flyDateFormatSize];
        byte[] src = new byte[srcM.length];
        System.arraycopy(srcM.getData(), srcM.index, src, 0, srcM.length);
        for(int i=0; i < 6; i++)
            System.arraycopy(src, plan[i][0], des, plan[i][1], plan[i][2]);
        return des;
    }
}
