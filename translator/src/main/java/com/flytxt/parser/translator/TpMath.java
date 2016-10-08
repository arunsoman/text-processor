package com.flytxt.parser.translator;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.MathUtils;
import org.apache.commons.math3.util.Precision;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;

public class TpMath extends Translator implements TpConstant {

	public final static byte dotByte = '.';

	public final static byte dotToken[] = { dotByte };

	public final static int numberLen = String.valueOf(Long.MAX_VALUE).length();
	public final byte[] doubleZero = ".0".getBytes();

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

	public Marker toMarker(double d, final MarkerFactory mf) {
		byte[] data = String.valueOf(d).getBytes();
		return mf.createImmutable(data, 0, data.length);
	}

	public Marker toMarker(long d, final MarkerFactory mf) {
		byte[] data = String.valueOf(d).getBytes();
		return mf.createImmutable(data, 0, data.length);
	}

	public Marker ceil(final byte[] data, final Marker m, final MarkerFactory mf) {
		byte[] d1 = m.getData() == null ? data : m.getData();
		double ceil = FastMath.ceil(Double.parseDouble(m.toString(d1)));
		byte[] result = String.valueOf(ceil).getBytes();
		return removeTrailingZeroz(result,mf);
	}

	public Marker floor(final byte[] data, final Marker m, final MarkerFactory mf) {
		byte[] d1 = m.getData() == null ? data : m.getData();
		double floor = FastMath.floor(Double.parseDouble(m.toString(d1)));
		byte[] result = String.valueOf(floor == 0 ? 0 : floor).getBytes();
		return removeTrailingZeroz(result,mf);
	}

	public Marker round(final byte[] data, final int scale, final Marker m, final MarkerFactory mf) {
		byte[] d1 = m.getData() == null ? data : m.getData();
		double d = Precision.round(Double.parseDouble(m.toString(d1)), scale, BigDecimal.ROUND_HALF_EVEN);
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
		boolean createImmutable = false;
		if (m.getData() != null) {
			data = m.getData();
			createImmutable = true;
		}
		int index = m.index;
		boolean found = false;
		for (final byte b : data) {
			if (b == dotByte) {
				found = true;
				break;
			}
			index++;
		}
		if (!found) {
			return mf.createImmutable(doubleZero, 0, doubleZero.length);
		}
		if(createImmutable){
			final int size = m.length - index+1;
			final byte[] result = new byte[size];
			System.arraycopy(data, index, result, 0, result.length);
			return mf.createImmutable(data, 0, size);
		}
		else{
			return mf.create(index, m.length-index);
		}
	}

	public boolean isNumber(final byte[] data, final Marker m, final MarkerFactory mf) {
		if (m.length > numberLen) {
			return false;
		}
		if (data[m.index] != '-' && (data[m.index] < start || data[m.index] > end)) {
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

	public Marker min(byte[]d1, Marker m1, byte[]d2, Marker m2, MarkerFactory mf){
		return lessEqThan(d1, m1, d2, m2, mf)? m1:m2;
	}
	public Marker max(byte[]d1, Marker m1, byte[]d2, Marker m2, MarkerFactory mf){
		return greaterEqThan(d1, m1, d2, m2, mf)? m1:m2;
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
		while(--ptr2>=ptr && (result[ptr2]== start || result[ptr2]== dotByte))
			;
		return mf.createImmutable(result, 0,ptr2+1);
	}
}
