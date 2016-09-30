package com.flytxt.parser.translator;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.commons.math3.util.MathUtils;
import org.apache.commons.math3.util.Precision;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;

public class TpMath extends Translator implements TpConstant {

	public final static byte dotByte = '.';

	public final static byte dotToken[] = { dotByte };

	public final static int numberLen = String.valueOf(Long.MAX_VALUE).length();

	public Marker abs(byte[] data, final Marker m, final MarkerFactory mf) {
		if (m.getData() != null) {
			data = m.getData();
		}
		if (data[m.index] == negative) {
			return mf.create(m.index + 1, m.length - 1);
		}
		return mf.create(m.index, m.length);
	}

	public boolean lessThan(byte[] d1, final Marker m1, byte[] d2, final Marker m2, final MarkerFactory mf) {
		return asDouble(d1, m1) < asDouble(d2, m2) ? true : false;
	}

	public boolean lessEqThan(byte[] d1, final Marker m1, byte[] d2, final Marker m2, final MarkerFactory mf) {

		return asDouble(d1, m1) <= asDouble(d2, m2) ? true : false;
	}

	public boolean greaterEqThan(final byte[] d1, final Marker m1, final byte[] d2, final Marker m2,
			final MarkerFactory mf) {
		return lessEqThan(d2, m2, d1, m1, mf);
	}

	public boolean greaterThan(final byte[] d1, final Marker m1, final byte[] d2, final Marker m2,
			final MarkerFactory mf) {
		return lessThan(d2, m2, d1, m1, mf);
	}

	public Marker subLong(byte[] d1, final Marker m1, byte[] d2, final Marker m2, final MarkerFactory mf) {

		if (m1.getData() != null) {
			d1 = m1.getData();
		}
		if (m2.getData() != null) {
			d2 = m2.getData();
		}
		final long res = asLong(d1, m1) - asLong(d2, m2);
		final byte[] resB = asByteArray(res);
		return mf.createImmutable(resB, 0, resB.length);
	}

	public Marker subDouble(byte[] d1, final Marker m1, byte[] d2, final Marker m2, final MarkerFactory mf) {
		if (m1.getData() != null) {
			d1 = m1.getData();
		}
		if (m2.getData() != null) {
			d2 = m2.getData();
		}
		final double res = asDouble(d1, m1) - asDouble(d2, m2);
		final byte[] resB = asByteArray(res);
		return mf.createImmutable(resB, 0, resB.length);
	}

	public Marker addLong(byte[] d1, final Marker m1, byte[] d2, final Marker m2, final MarkerFactory mf) {
		if (m1.getData() != null) {
			d1 = m1.getData();
		}
		if (m2.getData() != null) {
			d2 = m2.getData();
		}
		final long res = asLong(d1, m1) + asLong(d2, m2);
		final byte[] resB = asByteArray(res);
		return mf.createImmutable(resB, 0, resB.length);
	}

	public Marker addDouble(byte[] d1, final Marker m1, byte[] d2, final Marker m2, final MarkerFactory mf) {
		if (m1.getData() != null) {
			d1 = m1.getData();
		}
		if (m2.getData() != null) {
			d2 = m2.getData();
		}

		final long res = (long) (asDouble(d1, m1) * 1000 + asDouble(d2, m2) * 1000);
		final byte[] resB = asByteArray(((double) res / 1000));
		return mf.createImmutable(resB, 0, resB.length);
	}

	public Marker sub(final byte[] data, final Marker m, final int number, final MarkerFactory mf) {
		throw new RuntimeException();
	}

	public Marker add(final byte[] data, final Marker m, final int number, final MarkerFactory mf) {
		throw new RuntimeException();
	}

	public Marker ceil(final byte[] data, final Marker m, final MarkerFactory mf) {
		byte[] d1 = m.getData() == null ? data : m.getData();
		double ceil = Math.ceil(Double.parseDouble(m.toString(d1)));
		byte[] result = String.valueOf(ceil).getBytes();
		return removeTrailingZeroz(result,mf);
	}

	public Marker floor(final byte[] data, final Marker m, final MarkerFactory mf) {
		byte[] d1 = m.getData() == null ? data : m.getData();
		double floor = Math.floor(Double.parseDouble(m.toString(d1)));
		byte[] result = String.valueOf(floor == 0 ? 0 : floor).getBytes();
		return removeTrailingZeroz(result,mf);
	}

	public Marker round(final byte[] data, final int scale, final Marker m, final MarkerFactory mf) {
		byte[] d1 = m.getData() == null ? data : m.getData();
		double d = Precision.round(Double.parseDouble(m.toString(d1)), scale, BigDecimal.ROUND_CEILING);
		byte[] result = String.valueOf( d).getBytes();
		return removeTrailingZeroz(result,mf);
	}

	public boolean eq(byte[] d1, final Marker m1, byte[] d2, final Marker m2, final MarkerFactory mf) {
		if (m1.getData() != null) {
			d1 = m1.getData();
		}
		if (m2.getData() != null) {
			d2 = m2.getData();
		}
		if (m1.length != m2.length) {
			return false;
		}
		for (int i = 0; i < m1.length; i++) {
			if (d1[m1.index + i] != d2[m2.index + i]) {
				return false;
			}
		}
		return true;
	}

	public Marker extractDecimalIntegerPart(final byte[] data, final Marker m, final MarkerFactory mf) {
		return m.splitAndGetMarker(data, dotToken, 1, mf);
	}

	public Marker extractDecimalFractionPart(byte[] data, final Marker m, final MarkerFactory mf) {
		if (m.getData() != null) {
			data = m.getData();
		}
		int index = m.index;
		boolean found = false;
		for (final byte b : data) {
			if (b == dotByte) {
				found = true;
				index++;
				break;
			}
			index++;
		}
		if (!found) {
			final byte[] result = ".0".getBytes();
			return mf.createImmutable(result, 0, result.length);
			// TODO not sure if this is right
		}
		if (m.getData() != null) {
			final int size = m.length - index;
			//final byte[] result = new byte[size];
			//System.arraycopy(data, index, result, 0, result.length);
			return mf.createImmutable(data, 0, size);
		}
		return mf.create(index - 1, m.length - index + 1);
	}

	public boolean isNumber(final byte[] data, final Marker m, final MarkerFactory mf) {
		if (m.length > numberLen) {
			return false;
		}
		if (data[m.index] != '-' && (data[m.index] < start || data[m.index] >= end)) {
			return false;
		}
		boolean decimalSeperatorFound = false;
		for (int i = m.index + 1; i < m.length; i++) {
			if ((data[m.index] < start && data[m.index] > end)) {
				if (!decimalSeperatorFound && data[m.index] == '.') {
					decimalSeperatorFound = true;
					continue;
				}
				return false;
			}
		}
		return true;
	}

	private Marker removeTrailingZeroz(byte[] result, final MarkerFactory mf) {
		int ptr = result.length;
		boolean dotFound = false;
		while(--ptr >= 0){
			if(result[ptr] == dotByte){
				dotFound = true;
				break;
			}
		}
		if(!dotFound)
			return mf.createImmutable(result, 0,result.length);
		int ptr2 = result.length;
		while(--ptr2>ptr && result[ptr2]== start)
			;
		return mf.createImmutable(result, 0,ptr2);
	}
}
