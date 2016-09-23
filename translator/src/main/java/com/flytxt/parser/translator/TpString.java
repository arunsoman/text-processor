package com.flytxt.parser.translator;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;

public class TpString {
	public int length(byte[] data, Marker m, MarkerFactory mf) {
		return m.length; // will it come index-length
	}

	public boolean isNull(byte[] data, Marker m, MarkerFactory mf) {
		return m.length == 0;
	}

	public boolean startsWtih(byte[] data, int stIndex, byte[] token, int tokenStPtr, int tokenlength) {
		for (int i = 0; i < tokenlength; i++) {
			if (data[stIndex + i] != token[tokenStPtr + i])
				return false;
		}
		return true;
	}

	public byte[] toUpperCase(byte[] data, Marker m, MarkerFactory mf) {
		if (isEmpty(data))
			return data;
		for (int i = m.index; i < m.length; i++) {
			if (data[i] >= 'a' && data[i] <= 'z') {
				data[i] -= 32;
			}
		}

		return data;
	}

	public byte[] toLowerCase(byte[] data, Marker m, MarkerFactory mf) {
		if (isEmpty(data))
			return data;
		for (int i = m.index; i < m.length; i++) {
			if (data[i] >= 'A' && data[i] <= 'Z') {
				data[i] += 32;
			}
		}

		return data;
	}

	public byte[] toTitleCase(byte[] data, Marker m, MarkerFactory mf) {
		if (isEmpty(data))
			return data;
		if (data[m.index] >= 'a' && data[m.index] <= 'z')
			data[m.index] -= 32;
		return data;
	}
	
	public Marker lTrim(byte[] data, Marker m, MarkerFactory mf) {
		if (isEmpty(data))
			return m;
		 int start= m.index;
		 int end = m.length;
		 while ((start < end) && (data[start] <= ' ')) {
			 start++; 
		 }
		 if(start>0 && start <m.length){
			m.index=start;
			m.length=m.length-start;
		 }
		
		return m;
	}
	
	public Marker rTrim(byte[] data, Marker m, MarkerFactory mf) {
		if (isEmpty(data))
			return m;
		 int start= m.length;
		 int end = m.index;
		 while ((start < end) && (data[start] <= ' ')) {
			 start--; 
		 }
		 if(start>0 && start <m.length){
			m.index=start;
			m.length=m.length-start;
		 }
		
		return m;
	}
	public Marker trim(byte[] data, Marker m, MarkerFactory mf) {
		
		if (isEmpty(data))
			return m;
		 int start= m.index;
		 int end = m.length;
		 while ((start < end) && (data[start] <= ' ')) {
			 start++; 
		 }
		 while ((start < end) && (data[end] <= ' ')) {
			 end--; 
		 }
		 if(start>0 || end <m.length){
			m.index=start;
			m.length=end;
		 }
		
		return m;
	}

	private boolean isEmpty(byte[] array) {
		return array == null || array.length == 0;
	}
}
