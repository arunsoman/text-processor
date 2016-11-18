package com.flytxt.tp.translator.tpdateutils;

import java.text.ParseException;
import java.util.Arrays;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.flytxt.tp.marker.Marker;

class Translator{
    public static final String flyDateFormat = "ddMMyyyy HH:mm:ss.SZ";
    private static DateTimeFormatter flyFmt = DateTimeFormat.forPattern(flyDateFormat);
    private static final int flyDateFormatSize = flyDateFormat.length();

	private int[][]plan;
    private DateTimeFormatter srcFmt;
    private byte[] template = new byte[]{
    		48,48, 
    		48,48, 
    		48,48,48,48, 
    		((byte)' '),
    		48,48, ((byte)':'),
    		48,48, ((byte)':'),
    		48,48, ((byte)'.'), 
    		48, 
    		((byte)'+'),48,48,48,48 };
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
        byte[] res = (des == null || des.length != template.length)?
        		new byte[template.length]: des;
        
        byte[] src = srcM.getData();
        System.arraycopy(template, 0, res, 0, template.length);
        for(int i=0; i < plan.length; i++){
 //       	System.out.println("i:"+i +" :"+plan.length);
//        	System.out.println("Copying..." +new String(src, plan[i][0], plan[i][2])
//        			+" from  ; "+ plan[i][0] +" byte"+
//        Arrays.toString(new String(src, plan[i][0], plan[i][2]).getBytes()));
            System.arraycopy(src, plan[i][0], res, plan[i][1], plan[i][2]);
//            System.out.println(Arrays.toString(res)+" : "+new String(res));
        }
        return res;
    }
}
