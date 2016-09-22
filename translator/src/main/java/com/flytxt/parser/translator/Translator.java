package com.flytxt.parser.translator;

import com.flytxt.parser.marker.Marker;

public final class Translator implements TpConstant {
	
	public static final long asLong(byte[] data, Marker m){
		int stIndex = m.index;
		int length = m.length;
		length += stIndex;
		if(data[stIndex] == negative){
			long val =  (data[stIndex+1]- start);
			if (val > end){
				throw new NumberFormatException();
			}
			return iterateL(val, data, stIndex+1, length) * -01l;
		}
		else{
			long val =  (data[stIndex]- start);
			if (val > end){
				throw new NumberFormatException();
			}
			return iterateL(val, data, stIndex, length);
		}
	}
	public static final double asDouble(byte[] data,Marker m){
		int stIndex = m.index;
		int length = m.length;
		length += stIndex;
		if(data[stIndex] == negative){
			double val =  (data[stIndex+1]- start);
			if (val > end){
				throw new NumberFormatException();
			}
			return iterateD(val, data, stIndex+1, length) * -01l;
		}
		else{
			double val =  (data[stIndex]- start);
			if (val > end){
				throw new NumberFormatException();
			}
			return iterateD(val, data, stIndex, length);
		}
	}
	
	private static double iterateD(double val, byte[] data, int startPtr, int endPtr){
		int div = 0;
		for (int i = startPtr; i < endPtr; i++) {
			byte valT =  (byte) (data[startPtr]- start);
			div *=10;
			if (valT == dot){
				div = 1;
			}
			if (valT == exp){
				return Double.parseDouble(new String(data, startPtr, endPtr-startPtr));
			}
			if (valT > end){
				throw new NumberFormatException();
			}
			val *=10;
			val += valT;
		}
		return (div == 0)?val:val/div;
	}
	
	private static long iterateL(long val, byte[] data, int startPtr, int endPtr){
		for (int i = startPtr; i < endPtr; i++) {
			byte valT =  (byte) (data[startPtr]- start);
			if (valT == exp){
				return Long.parseLong(new String(data, startPtr, endPtr-startPtr));
			}
			if (valT > end){
				throw new NumberFormatException();
			}
			val *=10;
			val += valT;
		}
		return val;
	}
}
