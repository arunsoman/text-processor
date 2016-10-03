package com.flytxt.parser.lookup;

import com.flytxt.parser.lookup.node.CharPath;
import com.flytxt.parser.lookup.node.CharacterDic;

public class Search<T> extends Lookup {

    private final CharacterDic<T> dictionary = new CharacterDic<>();

    public void load(final byte[] key, final T val) {
        for (int i = 0; i < key.length; i++) {
            final CharPath<T> path = new CharPath<>(key, i, val);
            dictionary.getCharNode(key[i]).addPath(path);
        }
    }

    public void bake() {
        // fMap = new UnmodifiableTrie<byte[], Marker>(new PatriciaTrie(map));
    }

    public T get(final byte[] key) {
        return dictionary.getCharNode(key[0]).getValue(key);
    }
}
