package com.flytxt.parser.translator;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;

public class TpString {
	private static final byte smallA = 'a';
	private static final byte smallZ = 'z';
	private static final byte capsA = 'A';
	private static final byte capsZ = 'Z';
	private static final byte space = ' ';
	private static final byte deltaFromA2a = capsA-smallA;
	
	public int length(byte[] data, Marker m, MarkerFactory mf) {
		return m.length; // will it come index-length
	}

	public boolean isNull(byte[] data, Marker m, MarkerFactory mf) {
		return m == null || m.length == 0;
	}

	public boolean startsWtih(byte[] data, int stIndex, byte[] token, int tokenStPtr, int tokenlength) {
		for (int i = 0; i < tokenlength; i++) {
			if (data[stIndex + i] != token[tokenStPtr + i])
				return false;
		}
		return true;
	}

	public Marker toUpperCase(byte[] data, Marker m, MarkerFactory mf) {
		byte[] dest = new byte[m.length];
		for (int i = m.index; i < m.length; i++) {
			if (data[i] >= smallA && data[i] <= smallZ) {
				dest[i] = (byte) (data[i]+deltaFromA2a);
			}
		}
		return mf.createImmutable(dest, 0, m.length);
	}

	public Marker toLowerCase(byte[] data, Marker m, MarkerFactory mf) {
		byte[] dest = new byte[m.length];
		for (int i = m.index; i < m.length; i++) {
			if (data[i] >= smallA && data[i] <= smallZ) {
				dest[i] = (byte) (data[i]+deltaFromA2a);
			}
		}
		return mf.createImmutable(dest, 0, m.length);
	}

	public Marker toTitleCase(byte[] data, Marker m, MarkerFactory mf) {
		byte[] dest = new byte[m.length];
		System.arraycopy(data, m.index, dest, 0, m.length);
		if (data[m.index] >= 'a' && data[m.index] <= 'z')
			dest[m.index] += deltaFromA2a;
		return mf.createImmutable(dest, 0, m.length);
	}
	
	public Marker lTrim(byte[] data, Marker m, MarkerFactory mf) {
		 int start= m.index;
		 int end = start + m.length;
		 while ((start < end) && (data[start] == space)) {
			 start++; 
		 }
		return mf.create(start, m.length);
	}
	
	public Marker rTrim(byte[] data, Marker m, MarkerFactory mf) {
		 int start= m.index+m.length;
		 int end = m.index;
		 while ((start > end) && (data[start] == space)) {
			 start--; 
		 }
		return mf.create(m.index, m.length-start);
	}

	public Marker trim(byte[] data, Marker m, MarkerFactory mf) {
		 int start= m.index;
		 int end = start + m.length;
		 int mid = (start +end)/2;
		 boolean stopHead = false, stopTail = false;
		 while (start < mid){
			 if(!stopHead ){
				 if(data[start] == space)
					 start++;
				 else
					 stopHead = true;
			 }
			 if(!stopTail ){
				 if(data[end] == space)
					 end--;
				 else
					 stopTail = true;
			 }
			 if(data[end] == space)
				 end--;
		 }
		return mf.create(start, end-start);
	}
}
