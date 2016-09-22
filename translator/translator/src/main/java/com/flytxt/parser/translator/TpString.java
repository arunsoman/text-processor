package com.flytxt.parser.translator;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;

public class TpString {
	public int length(byte[] data, Marker m, MarkerFactory mf){
		return m.length;
	}

	public boolean isNull(byte[] data, Marker m, MarkerFactory mf){
		return m.length == 0;
	}
	public boolean startsWtih(byte[] data, int stIndex, byte[] token, int tokenStPtr, int tokenlength){
		for(int i =0; i <tokenlength; i++){
			if(data[stIndex+i] != token[tokenStPtr+i])
				return false;
		}
		return true;
	}
}
