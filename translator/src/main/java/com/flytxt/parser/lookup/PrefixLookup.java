package com.flytxt.parser.lookup;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.trie.PatriciaTrie;
import org.apache.commons.collections4.trie.UnmodifiableTrie;

public class PrefixLookup<T> extends Lookup<T> {

    private final Map<String, T> map = new HashMap<>();

    private UnmodifiableTrie<String, T> fMap;

    public PrefixLookup(final String file) {
        this.fileName = file;
        loadFromFile();
    }

    @Override
    public void load(final byte[] key, final T val) {
        map.put(new String(key), val);
    }

    public void bake() {
        fMap = new UnmodifiableTrie<>(new PatriciaTrie<>(map));
    }

    public T get(final String key) {
        return fMap.get(key);
    }
}
