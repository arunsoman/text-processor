package com.flytxt.parser.translator;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;

public class TpDate {
	public static String flyDateFormat = "ddmmyyyy HH:mm:ss"; 
	private static int[] yearArray = {4,5,6,7}; 
	private static int[] dateArray = {0,1}; 
	private static int[] monthArray = {2,3}; 
	private static int[] time = {9,10,12,13,15,16}; 
	
	public Marker convertDate(byte[] data, Marker m, MarkerFactory mf, String format){
		throw new RuntimeException();
	}
	
	public boolean after(byte[] data, Marker m, byte[] data2, Marker m2){
		for(int i : yearArray){
			if(data[m.index+i]> data2[m2.index+i])
				return true;
		}
		for(int i : monthArray){
			if(data[m.index+i]> data2[m2.index+i])
				return true;
		}
		for(int i : dateArray){
			if(data[m.index+i]> data2[m2.index+i])
				return true;
		}
		for(int i : time){
			if(data[m.index+i]> data2[m2.index+i])
				return true;
		}
		return false;
	}

	public boolean before(byte[] data, Marker m, byte[] data2, Marker m2){
		return ! after(data, m, data2, m2);
	}
	public long differenceInMillis(byte[] data, Marker m, byte[] data2, Marker m2){
		long diff = Translator.asLong(data2, m2)-Translator.asLong(data, m);
		return diff;
	}
}
