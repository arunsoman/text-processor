package com.flytxt.parser.translator;

import org.springframework.stereotype.Service;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;
@Service
public class TpString {

    private static final byte smallA = 'a';

    private static final byte smallZ = 'z';

    private static final byte capsA = 'A';

    private static final byte capsZ = 'Z';

    private static final byte space = ' ';

    private static final byte deltaFromA2a = capsA - smallA;

    public int length( Marker m) {
        return m.length; // will it come index-length
    }

    public boolean isNull( Marker m) {
        return m == null || m.length == 0;
    }

    public boolean endsWithIgnore( Marker dataMarker, final Marker prefixMarker) {

        final byte[] d1 = dataMarker.getData();
        final byte[] d2 = prefixMarker.getData();
        int prefixPtr = prefixMarker.index;
        int dataPtr = dataMarker.index + dataMarker.length - prefixMarker.length;
        for (int i = dataPtr; i < dataMarker.index + dataMarker.length; i++) {
            int diff = d1[i] - d2[prefixPtr++];
            if (diff != 0)
                if (Math.abs(diff) != 32)
                    return false;
        }
        return true;
    }

    public boolean endsWith( Marker dataMarker, final Marker prefixMarker) {

        final byte[] data1 = dataMarker.getData();
        final byte[] data2 = prefixMarker.getData() ;

        int dataIndex = dataMarker.index + dataMarker.length - 1;
        int prefixIndex = prefixMarker.index + prefixMarker.length - 1;
        for (int i = prefixMarker.index + prefixMarker.length; i > prefixMarker.index; i--) {
            if (data1[dataIndex] != data2[prefixIndex])
                return false;
            dataIndex--;
            prefixIndex--;
        }
        return true;
    }

    public boolean startsWith( Marker dataMarker, final Marker prefixMarker) {

        final byte[] data1 = dataMarker.getData();
        final byte[] data2 = prefixMarker.getData();
        if (dataMarker.length < prefixMarker.length)
            return false;
        int dataIndex = dataMarker.index + prefixMarker.length - 1;
        int prefixIndex = prefixMarker.index + prefixMarker.length - 1;
        for (int i = dataIndex; i >= prefixMarker.index; i--) {
            if (data1[dataIndex] != data2[prefixIndex])
                return false;
            dataIndex--;
            prefixIndex--;
        }
        return true;
    }

    public Marker toUpperCase( Marker m, MarkerFactory mf) {
        final byte[] data1 = m.getData();
        final byte[] dest = new byte[m.length];
        int index = 0;
        for (int i = m.index; i < m.length; i++)
            if (data1[i] >= smallA && data1[i] <= smallZ)
                dest[index++] = (byte) (data1[i] + deltaFromA2a);
            else
                dest[index++] = data1[i];
        return mf.createMarker(dest, 0, m.length);
    }

    public Marker toLowerCase( Marker m, MarkerFactory mf) {
        final byte[] data1 = m.getData();
        final byte[] dest = new byte[m.length];
        int index = 0;
        for (int i = m.index; i < m.length; i++)
            if (data1[i] >= capsA && data1[i] <= capsZ)
                dest[index++] = (byte) (data1[i] - deltaFromA2a);
            else
                dest[index++] = data1[i];
        return mf.createMarker(dest, 0, m.length);
    }

    public Marker toTitleCase( Marker m, MarkerFactory mf) {
        if (m.length == 0)
            return m;
        final byte[] data1 = m.getData();
        final byte[] dest = new byte[m.length];
        System.arraycopy(m.getData(), m.index, dest, 0, m.length);
        if (data1[m.index] >= 'a' && data1[m.index] <= 'z')
            dest[m.index] += deltaFromA2a;

        for (int i = 1; i < m.length; i++)
            if (dest[i] >= 'A' && dest[i] <= 'Z')
                dest[i] -= deltaFromA2a;

        return mf.createMarker(dest, 0, m.length);
    }

    public Marker lTrim( Marker m, MarkerFactory mf) {
        final byte[] data1 = m.getData();
        int start = m.index;
        final int end = start + m.length;
        while ((start < end) && (data1[start] == space))
            start++;
        return mf.createMarker(null,start, m.length - start);
    }

    public Marker rTrim( Marker m, MarkerFactory mf) {
        final byte[] data1 = m.getData();
        int start = m.index + m.length - 1;
        int dec = 0;
        final int end = m.index;
        while ((start >= end) && (data1[start] == space)) {
            start--;
            dec--;
        }
        return mf.createMarker(null,m.index, m.length + dec);
    }

    public Marker trim( Marker m, MarkerFactory mf) {
        final byte[] data1 = m.getData();
        int start = m.index;
        int end = start + m.length - 1;
        int newStart = start;
        int dec = 0;
        boolean headFound = false, tailFound = false;
        while (start <= end) {
            if (!headFound)
                if (data1[start] == space) {
                    start++;
                    dec--;
                } else {
                    newStart = start;
                    headFound = true;
                }
            if (!tailFound)
                if (data1[end] == space) {
                    end--;
                    dec--;
                } else
                    tailFound = true;
            if (headFound && tailFound)
                break;

        }
        int len = m.length + dec;
        if (len < 0)
            len = 0;
        return mf.createMarker(null,newStart, len);
    }

    public boolean contains( final Marker m1,  final Marker m2) {
        return indexOf( m1, m2) > -1;
    }

    public int indexOf(final Marker m1, final Marker m2) {
        final byte[] d1 = m1.getData();
        final byte[] d2 = m2.getData();
        for (int i = m1.index; i < m1.index + m1.length - m2.length + 1; ++i) {
            boolean found = true;
            for (int j = m2.index; j < m2.index + m2.length; ++j)
                if (d1[i + j] != d2[j]) {
                    found = false;
                    break;
                }
            if (found)
                return i;
        }
        return -1;
    }

    public boolean containsIgnoreCase(final Marker m1, final Marker m2) {
        return indexOIgnoreCase(m1, m2) > -1;
    }

    public int indexOIgnoreCase(final Marker m1, final Marker m2) {
        final byte[] d1 = m1.getData();
        final byte[] d2 = m2.getData();
        for (int i = m1.index; i < m1.index + m1.length - m2.length + 1; ++i) {
            boolean found = true;
            for (int j = m2.index; j < m2.index + m2.length; ++j) {
                int diff = d1[i + j] - d2[j];
                if (diff != 0)
                    if (Math.abs(diff) != 32) {
                        found = false;
                        break;
                    }
            }
            if (found)
                return i;
        }
        return -1;
    }

    public Marker extractLeading( Marker m1, final int extractCnt, MarkerFactory mf) {
        if (extractCnt < 0)
            throw new RuntimeException("extractCnt should be greater than 0 current:" + extractCnt);
        
        if (m1.getData() == null)
            return mf.createMarker(null,m1.index, extractCnt);
        else
            return mf.createMarker(m1.getData(), m1.index, extractCnt);
    }

    public Marker extractTrailing( Marker m1, final int extractCnt, MarkerFactory mf) {
        if (extractCnt < 0)
            throw new RuntimeException("extractCnt should be greater than 0 current:" + extractCnt);
        final byte[] data1 = m1.getData();
        if (data1 == null)
            return mf.createMarker(null,m1.index + m1.length - extractCnt, extractCnt);
        else
            return mf.createMarker(data1, m1.index + m1.length - extractCnt, extractCnt);
    }

    public Marker merge(final Marker m1,final Marker m2, MarkerFactory mf) {
        final byte[] d1 = m1.getData();
        final byte[] d2 = m2.getData();
        final byte[] result = new byte[m1.length + m2.length];
        // TODO
        System.arraycopy(d1, m1.index, result, 0, m1.length);
        System.arraycopy(d2, m2.index, result, m1.length, m2.length);
        return mf.createMarker(result, 0, result.length);
    }
}
