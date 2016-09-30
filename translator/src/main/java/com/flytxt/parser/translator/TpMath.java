package com.flytxt.parser.translator;

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

        int d1DecimalIndex = Integer.MIN_VALUE;
        int d2DecimalIndex = Integer.MIN_VALUE;

        int startIndex = 0;
        boolean isNegative = false;
        if (d1[0] == negative) {

            if (d2[0] != negative) {
                return true;
            }
            startIndex = 1;
            isNegative = true;
        }

        double m1DoubleValue = convertToNumber(d1[startIndex]);
        double m2DoubleValue = convertToNumber(d2[startIndex]);
        if (m1.getData() != null) {
            d1 = m1.getData();
        }
        if (m2.getData() != null) {
            d2 = m2.getData();
        }

        final int d1Lenght = d1.length;

        for (int i = 1 + startIndex; i < d1Lenght; i++) {

            if (d1[i] == dotByte) {
                d1DecimalIndex = i;
            } else {
                final int d1Value = convertToNumber(d1[i]);
                if (d1DecimalIndex > 0) {
                    m1DoubleValue = m1DoubleValue + Math.pow(10, d1DecimalIndex - i) * d1Value; // infeasible due to unpredictable behaviour
                } else {
                    m1DoubleValue = m1DoubleValue * Math.pow(10, i - startIndex) + d1Value;
                }
                if (d2.length <= i) {
                    if (isNegative) {
                        if (m1DoubleValue > m2DoubleValue) {
                            return true;
                        }
                    } else if (m1DoubleValue < m2DoubleValue) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            if (d2.length > i) {
                if (d2[i] == dotByte) {
                    d2DecimalIndex = i;
                } else {

                    final int d2Value = convertToNumber(d2[i]);

                    if (d2DecimalIndex > 0) {
                        m2DoubleValue = m2DoubleValue + Math.pow(10, d2DecimalIndex - i) * d2Value;
                    } else {
                        m2DoubleValue = m2DoubleValue * Math.pow(10, i - startIndex) + d2Value;
                    }
                }
            }
        }
        if (isNegative) {
            if (m1DoubleValue > m2DoubleValue) {
                return true;
            }
        } else if (m1DoubleValue < m2DoubleValue) {
            return true;
        }
        return false;

    }

    private int convertToNumber(final byte d2) {
        final int number = d2 - '0';
        if (number > 9) {
            throw new NumberFormatException("NOT A NUMBER");
        }
        return number;
    }

    public boolean lessEqThan(byte[] d1, final Marker m1, byte[] d2, final Marker m2, final MarkerFactory mf) {

        int d1DecimalPoint = Integer.MIN_VALUE;
        int d2DecimalPoint = Integer.MIN_VALUE;

        int startIndex = 0;
        boolean isNegative = false;
        if (d1[0] == negative) {

            if (d2[0] != negative) {
                return true;
            }
            startIndex = 1;
            isNegative = true;
        }

        double m1DoubleValue = convertToNumber(d1[startIndex]);
        double m2DoubleValue = convertToNumber(d2[startIndex]);
        if (m1.getData() != null) {
            d1 = m1.getData();
        }
        if (m2.getData() != null) {
            d2 = m2.getData();
        }

        final int d1Lenght = d1.length;

        for (int i = 1 + startIndex; i < d1Lenght; i++) {

            if (d1[i] == dotByte) {
                d1DecimalPoint = i;
            }
            final int d1Value = convertToNumber(d1[i]);
            if (d1DecimalPoint > 0) {
                m1DoubleValue = m1DoubleValue + Math.pow(10, d1DecimalPoint - i) * d1Value;
            } else {
                m1DoubleValue = m1DoubleValue * Math.pow(10, i - startIndex) + d1Value;
            }
            if (d2.length <= i) {
                if (isNegative) {
                    if (m1DoubleValue >= m2DoubleValue) {
                        return true;
                    }
                } else if (m1DoubleValue <= m2DoubleValue) {
                    return true;
                } else {
                    return false;
                }
            }
            if (d2.length > i) {
                if (d2[i] == dotByte) {
                    d2DecimalPoint = i;
                }

                final int d2Value = convertToNumber(d2[i]);

                if (d2DecimalPoint > 0) {
                    m2DoubleValue = m2DoubleValue + Math.pow(10, d2DecimalPoint - i) * d2Value;
                } else {
                    m2DoubleValue = m2DoubleValue * Math.pow(10, i - startIndex) + d2Value;
                }
            }
        }
        if (isNegative) {
            if (m1DoubleValue >= m2DoubleValue) {
                return true;
            }
        } else if (m1DoubleValue <= m2DoubleValue) {
            return true;
        }
        return false;
    }

    public boolean greaterEqThan(final byte[] d1, final Marker m1, final byte[] d2, final Marker m2, final MarkerFactory mf) {
        return lessEqThan(d2, m2, d1, m1, mf);
    }

    public boolean greaterThan(final byte[] d1, final Marker m1, final byte[] d2, final Marker m2, final MarkerFactory mf) {
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
        final double res = asDouble(d1, m1) + asDouble(d2, m2);
        final byte[] resB = asByteArray(res);
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
        byte[] result = String.valueOf(Math.ceil(Double.parseDouble(m.toString(d1)))).getBytes();
        return mf.createImmutable(result, 0, result.length);
    }

    public Marker floor(final byte[] data, final Marker m, final MarkerFactory mf) {
    	byte[] d1 = m.getData() == null ? data : m.getData();
        byte[] result = String.valueOf(Math.floor(Double.parseDouble(m.toString(d1)))).getBytes();
        return mf.createImmutable(result, 0, result.length);
    }

    public Marker round(final byte[] data, final int index, final Marker m, final MarkerFactory mf) {
    	byte[] d1 = m.getData() == null ? data : m.getData();
    	int val = 1;
    	for(int i = 0; i< index;i++){
    		val*=10;
    	}
    	double d = Math.round(Double.parseDouble(m.toString(d1))*val)/val; 
        byte[] result = String.valueOf(d==0?0:d).getBytes();
        return mf.createImmutable(result, 0, result.length);
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
            final byte[] result = new byte[size];
            System.arraycopy(data, index, result, 0, result.length);
            return mf.createImmutable(result, 0, result.length);
        }
        return mf.create(index - 1, m.length - index + 1);
    }

    public boolean isNumber(final byte[] data, final Marker m, final MarkerFactory mf) {
        if (m.length > numberLen) {
            return false;
        }
        if (data[m.index] != '-' && (data[m.index] <= start && data[m.index] >= end)) {
            return false;
        }
        for (int i = m.index + 1; i < m.length; i++) {
            if ((data[m.index] <= start && data[m.index] >= end)) {
                return false;
            }
        }
        return true;
    }

    /*
     * private void addByte(byte[] A , byte[] B){ List<Byte> array = new ArrayList<Byte>(A); for (int i = 0; i < B.length; i++) array = _add(array, B[i], i);
     *
     * } private void _add(byte[] A, byte b,int idx){
     *
     *
     * }
     */
}
