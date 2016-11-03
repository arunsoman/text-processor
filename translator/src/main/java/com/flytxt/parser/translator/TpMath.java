package com.flytxt.parser.translator;

import java.math.BigDecimal;

import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.Precision;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;
public class TpMath extends Translator {

    public final static byte dotByte = '.';

    public final static byte dotToken[] = { dotByte };

    public final static int numberLen = String.valueOf(Long.MAX_VALUE).length();
    public final byte[] doubleZero = ".0".getBytes();

    public Marker abs(final Marker m, final MarkerFactory mf) {
        byte[]data = m.getData();
        if (data[m.index] == negative)
            return mf.createMarker(null,m.index + 1, m.length - 1);
        return mf.createMarker(null,m.index, m.length);
    }

    public boolean lessThan(final Marker m1, final Marker m2, final MarkerFactory mf) {
        return asDouble(m1) < asDouble(m2) ? true : false;
    }

    public boolean lessEqThan(final Marker m1, final Marker m2, final MarkerFactory mf) {

        return asDouble(m1) <= asDouble(m2) ? true : false;
    }

    public boolean greaterEqThan(final Marker m1, final Marker m2,
            final MarkerFactory mf) {
        return lessEqThan(m2, m1, mf);
    }

    public boolean greaterThan(final Marker m1, final Marker m2,
            final MarkerFactory mf) {
        return lessThan(m2, m1, mf);
    }

    public Marker subLong(final Marker m1, final Marker m2, final MarkerFactory mf) {

        final long res = asLong(m1) - asLong( m2);
        final byte[] resB = asByteArray(res);
        return mf.createMarker(resB, 0, resB.length);
    }

    public Marker subDouble(final Marker m1, final Marker m2, final MarkerFactory mf) {

        final double res = asDouble( m1) - asDouble( m2);
        final byte[] resB = asByteArray(res);
        return mf.createMarker(resB, 0, resB.length);
    }

    public Marker addLong(final Marker m1, final Marker m2, final MarkerFactory mf) {

        final long res = asLong(m1) + asLong(m2);
        final byte[] resB = asByteArray(res);
        return mf.createMarker(resB, 0, resB.length);
    }

    public Marker addDouble(final Marker m1, final Marker m2, final MarkerFactory mf) {
        final long res = (long) (asDouble( m1) * 1000 + asDouble( m2) * 1000);
        final byte[] resB = asByteArray(((double) res / 1000));
        return mf.createMarker(resB, 0, resB.length);
    }

    public Marker mulDouble(final Marker m1, final Marker m2, final MarkerFactory mf) {
        final byte[] resB = asByteArray(asDouble( m1)*asDouble( m2));
        return mf.createMarker(resB, 0, resB.length);
    }

    public Marker divDouble(final Marker m1, final Marker m2, final MarkerFactory mf) {
        final byte[] resB = asByteArray(asDouble( m1)/asDouble( m2));
        return mf.createMarker(resB, 0, resB.length);
    }

    public Marker toMarker(double d, final MarkerFactory mf) {
        byte[] data = String.valueOf(d).getBytes();
        return mf.createMarker(data, 0, data.length);
    }

    public Marker toMarker(long d, final MarkerFactory mf) {
        byte[] data = String.valueOf(d).getBytes();
        return mf.createMarker(data, 0, data.length);
    }

    public Marker ceil(final Marker m, final MarkerFactory mf) {
        //byte[] d1 = m.getData();
        double ceil = FastMath.ceil(Double.parseDouble(m.toString()));
        byte[] result = String.valueOf(ceil).getBytes();
        return removeTrailingZeroz(result,mf);
    }

    public Marker floor(final Marker m, final MarkerFactory mf) {
        //byte[] d1 = m.getData() == null ? data : m.getData();
        double floor = FastMath.floor(Double.parseDouble(m.toString()));
        byte[] result = String.valueOf(floor == 0 ? 0 : floor).getBytes();
        return removeTrailingZeroz(result,mf);
    }

    public Marker round(final int scale, final Marker m, final MarkerFactory mf) {
        //byte[] d1 = m.getData();
        double d = Precision.round(Double.parseDouble(m.toString()), scale, BigDecimal.ROUND_HALF_EVEN);
        byte[] result = String.valueOf( d).getBytes();
        return removeTrailingZeroz(result,mf);
    }

    public boolean eq(final Marker m1, final Marker m2, final MarkerFactory mf) {
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
        Marker result = mf.createMarker(m.getData(), m.index, m.length);
        m.splitAndGetMarkers(dotToken, new int[]{1}, mf, result);
        return result;
    }

    public Marker extractDecimalFractionPart(final Marker m, final MarkerFactory mf) {
        boolean createImmutable = false;
        byte[] data = m.getData();
        int index = m.index;
        boolean found = false;
        for (final byte b : data) {
            if (b == dotByte) {
                found = true;
                break;
            }
            index++;
        }
        if (!found)
            return mf.createMarker(doubleZero, 0, doubleZero.length);
        if(createImmutable){
            final int size = m.length - index+1;
            final byte[] result = new byte[size];
            System.arraycopy(data, index, result, 0, result.length);
            return mf.createMarker(data, 0, size);
        } else
            return mf.createMarker(null,index, m.length-index);
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

    public Marker min( Marker m1, Marker m2, MarkerFactory mf){
        return lessEqThan( m1,m2, mf)? m1:m2;
    }
    public Marker max(Marker m1, Marker m2, MarkerFactory mf){
        return greaterEqThan(m1, m2, mf)? m1:m2;
    }
    private Marker removeTrailingZeroz(byte[] result, final MarkerFactory mf) {
        int ptr = result.length;
        boolean dotFound = false;
        while(--ptr >= 0)
            if(result[ptr] == dotByte){
                dotFound = true;
                break;
            }
        if(!dotFound)
            return mf.createMarker(result, 0,result.length);
        int ptr2 = result.length;
        while(--ptr2>=ptr && (result[ptr2]== start || result[ptr2]== dotByte))
            ;
        return mf.createMarker(result, 0,ptr2+1);
    }
}
