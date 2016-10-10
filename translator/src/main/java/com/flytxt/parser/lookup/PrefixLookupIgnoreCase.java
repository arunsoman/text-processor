package com.flytxt.parser.lookup;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.trie.PatriciaTrie;
import org.apache.commons.collections4.trie.UnmodifiableTrie;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;
import com.flytxt.parser.translator.TpString;

public class PrefixLookupIgnoreCase<T> extends Lookup<T> {

    private final Map<byte[], T> map = new HashMap<>();

    private UnmodifiableTrie<byte[], Marker> fMap;

    private final MarkerFactory mf = new MarkerFactory();

    private final TpString tpString = new TpString();

    private final byte capsA = 'A';

    private final byte capsZ = 'Z';

    public PrefixLookupIgnoreCase(final String file) {
        this.fileName = file;
        loadFromFile();
    }

    private byte[] toLower(final byte[] data1) {
        final byte[] dest = new byte[data1.length];
        int index = 0;
        for (final byte element : data1) {
            if (element >= capsA && element <= capsZ) {
                dest[index++] = (byte) (element - 'A' - 'a');
            } else {
                dest[index++] = element;
            }
        }
        return dest;
    }

    @Override
    public void load(final byte[] key, final T val) {
        map.put(toLower(key), val);
    }

    public void bake() {
        fMap = new UnmodifiableTrie<>(new PatriciaTrie(map));
    }

    public Marker get(final byte[] key) {
        return fMap.get(toLower(key));
    }

}
