package com.flytxt.parser.translator;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;

public class TpMath extends Translator implements TpConstant {
	public final static byte dotByte = '.';
	public final static byte dotToken[] = { dotByte };
	public final static int numberLen = String.valueOf(Long.MAX_VALUE).length();

	public Marker abs(byte[] data, Marker m, MarkerFactory mf) {
		if (m.getData() != null)
			data = m.getData();
		if (data[m.index] == negative) {
			return mf.create(m.index+1, m.length-1);
		}
		return mf.create(m.index, m.length);
	}

	public boolean lessThan(byte[] d1, Marker m1, byte[] d2, Marker m2, MarkerFactory mf) {
		boolean isM1Double = false;
		boolean isM2Double = false;
		if (m1.getData() != null)
			d1 = m1.getData();
		if (m2.getData() != null)
			d2 = m2.getData();

		for(byte b: d1){
			if(b == dotByte)
				isM1Double = true;
		}

		for(byte b: d2){
			if(b == dotByte)
				isM2Double = true;
		}
		if (isM1Double || isM2Double) {
			return Double.parseDouble(m1.toString(d1)) <= Double.parseDouble(m2.toString(d2)) ;
		}
		return Long.parseLong(m1.toString(d1)) <= Long.parseLong(m1.toString(d1));

	}

	public boolean lessEqThan(byte[] d1, Marker m1, byte[] d2, Marker m2, MarkerFactory mf) {
		boolean isM1Double = false;
		boolean isM2Double = false;
		if (m1.getData() != null)
			d1 = m1.getData();
		if (m2.getData() != null)
			d2 = m2.getData();

		for(byte b: d1){
			if(b == dotByte)
				isM1Double = true;
		}

		for(byte b: d2){
			if(b == dotByte)
				isM2Double = true;
		}
		if (isM1Double || isM2Double) {
			return Double.parseDouble(m1.toString(d1)) <= Double.parseDouble(m2.toString(d2)) ;
		}
		return Long.parseLong(m1.toString(d1)) <= Long.parseLong(m1.toString(d1));
	}

	public boolean greaterEqThan(byte[] d1, Marker m1, byte[] d2, Marker m2, MarkerFactory mf) {
		return lessEqThan(d2, m2, d1, m1, mf);
	}

	public boolean greaterThan(byte[] d1, Marker m1, byte[] d2, Marker m2, MarkerFactory mf) {
		return lessThan(d2, m2, d1, m1, mf);
	}

	public Marker subLong(byte[] d1, Marker m1, byte[] d2, Marker m2, MarkerFactory mf) {

		if (m1.getData() != null)
			d1 = m1.getData();
		if (m2.getData() != null)
			d2 = m2.getData();
		long res = asLong(d1, m1) - asLong(d2, m2);
		byte[] resB = asByteArray(res);
		return mf.createImmutable(resB, 0, resB.length);
	}

	public Marker subDouble(byte[] d1, Marker m1, byte[] d2, Marker m2, MarkerFactory mf) {
		if (m1.getData() != null)
			d1 = m1.getData();
		if (m2.getData() != null)
			d2 = m2.getData();
		double res = asDouble(d1, m1) - asDouble(d2, m2);
		byte[] resB = asByteArray(res);
		return mf.createImmutable(resB, 0, resB.length);
	}

	public Marker addLong(byte[] d1, Marker m1, byte[] d2, Marker m2, MarkerFactory mf) {
		if (m1.getData() != null)
			d1 = m1.getData();
		if (m2.getData() != null)
			d2 = m2.getData();
		long res = asLong(d1, m1) + asLong(d2, m2);
		byte[] resB = asByteArray(res);
		return mf.createImmutable(resB, 0, resB.length);
	}

	public Marker addDouble(byte[] d1, Marker m1, byte[] d2, Marker m2, MarkerFactory mf) {
		if (m1.getData() != null)
			d1 = m1.getData();
		if (m2.getData() != null)
			d2 = m2.getData();
		double res = asDouble(d1, m1) + asDouble(d2, m2);
		byte[] resB = asByteArray(res);
		return mf.createImmutable(resB, 0, resB.length);
	}

	public Marker sub(byte[] data, Marker m, int number, MarkerFactory mf) {
		throw new RuntimeException();
	}

	public Marker add(byte[] data, Marker m, int number, MarkerFactory mf) {
		throw new RuntimeException();
	}

	public Marker ceil(byte[] data, Marker m,  MarkerFactory mf) {
		throw new RuntimeException();
	}

	public Marker floor(byte[] data, Marker m, MarkerFactory mf) {
		throw new RuntimeException();
	}

	public Marker round(byte[] data, Marker m, MarkerFactory mf) {
		throw new RuntimeException();
	}

	public boolean eq(byte[] d1, Marker m1, byte[] d2, Marker m2, MarkerFactory mf) {
		if (m1.getData() != null)
			d1 = m1.getData();
		if (m2.getData() != null)
			d2 = m2.getData();
		if (m1.length != m2.length)
			return false;
		for (int i = 0; i < m1.length; i++) {
			if (d1[m1.index + i] != d2[m2.index + i])
				return false;
		}
		return true;
	}

	public Marker extractDecimalIntegerPart(byte[] data, Marker m, MarkerFactory mf) {
		return m.splitAndGetMarker(data, dotToken, 1, mf);
	}

	public Marker extractDecimalFractionPart(byte[] data, Marker m, MarkerFactory mf) {
		if(m.getData() != null)
			data = m.getData();
		int index = m.index;
		boolean found = false;
		for(byte b: data){
			if(b == dotByte){
				found = true;
				index++;
				break;
			}
			index++;
		}
		if(!found)
			return null;
		if(m.getData() != null){
			int size = m.length - index;
			byte[] result = new byte[size];
			System.arraycopy(data, index, result, 0, result.length);
			return mf.createImmutable(result, 0, result.length);
		}
		return mf.create(index, m.length- index);
	}

	public boolean isNumber(byte[] data, Marker m, MarkerFactory mf) {
		if (m.length > numberLen)
			return false;
		if(data[m.index] != '-' && (data[m.index] <= start && data[m.index] >= end))
			return false;
		for (int i = m.index+1; i < m.length; i++) {
			if( (data[m.index] <= start && data[m.index] >= end))
				return false;
		}
		return true;
	}
}
