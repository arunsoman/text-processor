package com.flytxt.parser.translator;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;

public class TpMath extends Translator implements TpConstant {
	public final static byte dotToken[] = { 0x2E };
	public final static byte dotByte = 0x2E;
	public final static int numberLen = String.valueOf(Long.MAX_VALUE).length();

	public Marker abs(byte[] data, Marker m, MarkerFactory mf) {
		if (m.getData() != null)
			data = m.getData();
		if (data[m.index] == negative) {
			byte[] b = new byte[m.length - 1];
			System.arraycopy(data, m.index + 1, b, 0, m.length);
		}
		return mf.createImmutable(data, m.index, m.length);
	}

	public boolean lessThan(byte[] d1, Marker m1, byte[] d2, Marker m2, MarkerFactory mf) {
		boolean isM1Long = false;
		boolean isM2Long = false;
		if (m1.getData() != null)
			d1 = m1.getData();
		if (m2.getData() != null)
			d2 = m2.getData();
		if (m1.splitAndGetMarker(d1, dotToken, 2, mf) == null) {
			isM1Long = true;
		}
		if (m2.splitAndGetMarker(d2, dotToken, 2, mf) == null) {
			isM2Long = true;
		}
		if (isM1Long & isM2Long) {
			return asLong(d1, m1) < asLong(d2, m2);
		}
		return asDouble(d1, m1) < asDouble(d2, m2);
	}

	public boolean lessEqThan(byte[] d1, Marker m1, byte[] d2, Marker m2, MarkerFactory mf) {
		boolean isM1Long = false;
		boolean isM2Long = false;
		if (m1.getData() != null)
			d1 = m1.getData();
		if (m2.getData() != null)
			d2 = m2.getData();
		if (m1.splitAndGetMarker(d1, dotToken, 2, mf) == null) {
			isM1Long = true;
		}
		if (m2.splitAndGetMarker(d2, dotToken, 2, mf) == null) {
			isM2Long = true;
		}
		if (isM1Long & isM2Long) {
			return asLong(d1, m1) <= asLong(d2, m2);
		}
		return asDouble(d1, m1) <= asDouble(d2, m2);
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
		double res = asDouble(d1, m1) - asDouble(d2, m2);
		byte[] resB = asByteArray(res);
		return mf.createImmutable(resB, 0, resB.length);
	}

	public Marker sub(byte[] data, Marker m, int number, MarkerFactory mf) {
		throw new RuntimeException();
	}

	public Marker add(byte[] data, Marker m, int number, MarkerFactory mf) {
		throw new RuntimeException();
	}

	public Marker ceil(byte[] data, Marker m, int number, MarkerFactory mf) {
		throw new RuntimeException();
	}

	public Marker floor(byte[] data, Marker m, int number, MarkerFactory mf) {
		throw new RuntimeException();
	}

	public Marker round(byte[] data, Marker m, int number, MarkerFactory mf) {
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
		return m.splitAndGetMarker(data, dotToken, 2, mf);
	}

	public Marker isNumber(byte[] data, Marker m, MarkerFactory mf) {
		if (m.length > numberLen)
			return booleanFalseMarker;
		int eCounter = 0;
		for (int i = m.index; i < m.length; i++) {
			if (data[i] - start > 9 || eCounter > 1 || data[i] != exp)
				return booleanFalseMarker;
		}
		return booleanTrueMarker;
	}
}
