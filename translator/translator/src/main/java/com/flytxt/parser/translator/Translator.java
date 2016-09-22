package com.flytxt.parser.translator;

public class Translator {
	private byte negative = (byte)'-';
	private byte start = 0x30;
	private byte end = 0x39;
	private byte exp = (byte) ((byte)'e' -start);
	private byte dot = (byte) (0x2e -start);
	
	public boolean startsWtih(byte[] data, int stIndex, byte[] token, int tokenStPtr, int tokenlength){
		for(int i =0; i <tokenlength; i++){
			if(data[stIndex+i] != token[tokenStPtr+i])
				return false;
		}
		return true;
	}
	public long asLong(byte[] data, int stIndex, int length){
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
	public double asDouble(byte[] data, int stIndex, int length){
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
	private double iterateD(double val, byte[] data, int startPtr, int endPtr){
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
	private long iterateL(long val, byte[] data, int startPtr, int endPtr){
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
