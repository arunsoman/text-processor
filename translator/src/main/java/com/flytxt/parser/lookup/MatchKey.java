package com.flytxt.parser.lookup;

import com.flytxt.parser.lookup.node.Node;

public class MatchKey<T> extends Lookup {

    private final Node node = new Node();

    public void load(final byte[] key, final T val) {
        node.add(key, val);
    }

    public void bake() {
        // fMap = new UnmodifiableTrie<byte[], Marker>(new PatriciaTrie(map));
    }

    public T get(final byte[] search) {
        return (T) node.traverse(search);
    }
}
