package com.flytxt.tp.lookup;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.trie.PatriciaTrie;
import org.apache.commons.collections4.trie.UnmodifiableTrie;

import com.flytxt.tp.marker.MarkerFactory;

public class PrefixLookup<T> extends Lookup<T> {

    private final Map<String, T> map = new HashMap<>();

    private UnmodifiableTrie<String, T> fMap;

    public PrefixLookup(final MarkerFactory mf) {
        this.mf = mf;
    }

    public PrefixLookup(final String file) {
        this.fileName = file;
        loadFromFile();
    }

    public PrefixLookup(final File file, MarkerFactory mf) {
        this.fileName = file.getAbsolutePath();
        this.mf = mf;
        loadFromFile();
    }

    @Override
    public void load(final byte[] key, final T val) {
        map.put(new String(key), val);
    }

    @Override
    public void bake() {
        fMap = new UnmodifiableTrie<>(new PatriciaTrie<>(map));
    }

    @Override
    public T get(byte[] key) {
        return fMap.get(new String(key));
    }

}
