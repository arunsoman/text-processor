package com.flytxt.parser.translator;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;

public class TpMath implements TpConstant {
	public final static byte dotToken[]= {0x2E};
	public final static byte dotByte= 0x2E;
	public final static int numberLen = String.valueOf(Long.MAX_VALUE).length();
	
	public Marker abs(byte[] data, Marker m, MarkerFactory mf){
		if(data[m.index] == negative){
			byte[] b = new byte[m.length-1];
			System.arraycopy(data, m.index+1, b, 0, m.length);
		}
		return mf.createImmutable(data,m, m.index, m.length);
	}
	
	public Marker extractDecimal(byte[] data, Marker m, MarkerFactory mf){
		return m.splitAndGetMarker(data, dotToken, m.index, mf);
	}
	
	public boolean isNumber(byte[] data, Marker m, MarkerFactory mf){
		if(m.length> numberLen)
			return false;
		int eCounter = 0;
		for(int i = m.index; i < m.length; i++){
			if(data[i]-start> 9 || eCounter >1 || data[i] != exp )
				return false;
		}
		return true;
	}
}
