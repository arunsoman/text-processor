package com.flytxt.tp.translator;

import java.math.BigDecimal;

import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.Precision;

import com.flytxt.tp.marker.ConstantMarker;
import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.marker.MarkerFactory;
import com.flytxt.tp.marker.Router;

public class TpMath implements ConstantMarker {

	private static final Router INDEX_OF_ZERO = new Router(new int[] { 0 });

	public final static byte dotByte = '.';

	public final static byte dotToken[] = { dotByte };

	public final static int numberLen = String.valueOf(Long.MAX_VALUE).length();
	public final byte[] doubleZero = ".0".getBytes();

	public Marker abs(final Marker m, final MarkerFactory mf) {
		byte[] data = m.getData();
		if (data[m.index] == negative)
			return mf.createMarker(data, m.index + 1, m.length - 1);
		return mf.createMarker(data, m.index, m.length);
	}

	public boolean lessThan(final Marker m1, final Marker m2, final MarkerFactory mf) {
		return m1.asDouble() < m2.asDouble() ? true : false;
	}

	public boolean lessEqThan(final Marker m1, final Marker m2, final MarkerFactory mf) {
		return m1.asDouble() <= m2.asDouble() ? true : false;
	}

	public boolean greaterEqThan(final Marker m1, final Marker m2, final MarkerFactory mf) {
		return lessEqThan(m2, m1, mf);
	}

	public boolean greaterThan(final Marker m1, final Marker m2, final MarkerFactory mf) {
		return lessThan(m2, m1, mf);
	}

	public Marker subLong(final Marker m1, final Marker m2, final MarkerFactory mf) {
		final long res = (m1.asLong() - m2.asLong());
		return mf.createMarker(res);
	}

	public Marker subDouble(final Marker m1, final Marker m2, final MarkerFactory mf) {
		final double res = m1.asDouble() - m2.asDouble();
		return mf.createMarker(res);
	}

	public Marker addLong(final Marker m1, final Marker m2, final MarkerFactory mf) {
		final long res = (m1.asLong()) + (m2.asLong());
		return mf.createMarker(res);
	}

	public Marker addDouble(final Marker m1, final Marker m2, final MarkerFactory mf) {
		final long res = (long) (m1.asDouble() * 1000 + m2.asDouble() * 1000);
		return mf.createMarker(((double) res / 1000));
	}

	public Marker mulDouble(final Marker m1, final Marker m2, final MarkerFactory mf) {
		return mf.createMarker(m1.asDouble() * m2.asDouble());
	}

	public Marker divDouble(final Marker m1, final Marker m2, final MarkerFactory mf) {
		return mf.createMarker(m1.asDouble() / m2.asDouble());
	}

	public Marker toMarker(double d, final MarkerFactory mf) {
		return mf.createMarker(d);
	}

	public Marker toMarker(long d, final MarkerFactory mf) {
		return mf.createMarker(d);
	}

	public Marker ceil(final Marker m, final MarkerFactory mf) {
		double ceil = FastMath.ceil(m.asDouble());
		return mf.createMarker(ceil);
	}

	public Marker floor(final Marker m, final MarkerFactory mf) {
		double floor = FastMath.floor(m.asDouble());
		return mf.createMarker(floor);
	}

	public Marker round(final int scale, final Marker m, final MarkerFactory mf) {
		double d = Precision.round(m.asDouble(), scale, BigDecimal.ROUND_HALF_EVEN);
		return mf.createMarker(d);
	}

	public boolean eq(final Marker m1, final Marker m2, final MarkerFactory mf) {
		if(m1.getDataType() == Marker.doubleDataType &&
				m2.getDataType() == Marker.doubleDataType)
			return m1.asDouble()==m2.asDouble();
		if(m1.getDataType() == Marker.longDataType &&
				m2.getDataType() == Marker.longDataType)
			return m1.asLong()==m2.asLong();
		
		byte[] d1 = m1.getData();
		byte[] d2 = m2.getData();
		if (m1.length != m2.length)
			return false;
		for (int i = 0; i < m1.length; i++)
			if (d1[m1.index + i] != d2[m2.index + i])
				return false;
		return true;
	}

	public Marker extractDecimalIntegerPart(final Marker m, final MarkerFactory mf) {
		if (m.getDataType() == Marker.doubleDataType)
			return mf.createMarker((long) m.asDouble());
		Marker result = mf.createMarker(m.getData(), m.index, m.length);
		m.splitAndGetMarkers(dotToken, INDEX_OF_ZERO, mf, result);
		return result;
	}

	public Marker extractDecimalFractionPart(final Marker m, final MarkerFactory mf) {
		double d = m.asDouble();
		double result =d - ((long)d);
		return mf.createMarker(result);
	}

	public boolean isNumber(final Marker m, final MarkerFactory mf) {
		byte[] data = m.getData();
		if (m.length > numberLen)
			return false;
		if (data[m.index] != '-' && (data[m.index] < start || data[m.index] > end))
			return false;
		boolean decimalSeperatorFound = false;
		for (int i = m.index + 1; i < m.length; i++)
			if ((data[m.index] < start && data[m.index] > end)) {
				if (!decimalSeperatorFound && data[m.index] == '.') {
					decimalSeperatorFound = true;
					continue;
				}
				return false;
			}
		return true;
	}

	public Marker min(Marker m1, Marker m2, MarkerFactory mf) {
		return lessEqThan(m1, m2, mf) ? m1 : m2;
	}

	public Marker max(Marker m1, Marker m2, MarkerFactory mf) {
		return greaterEqThan(m1, m2, mf) ? m1 : m2;
	}
}
