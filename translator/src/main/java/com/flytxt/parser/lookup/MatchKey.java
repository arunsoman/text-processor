package com.flytxt.parser.lookup;

import org.springframework.stereotype.Component;

import com.flytxt.parser.lookup.node.Node;

@Component
public class MatchKey<T> extends Lookup<T> {

    private final Node<T> node = new Node<>();

    public MatchKey(final String file) {
        this.fileName = file;
        loadFromFile();
    }

    @Override
    public void load(final byte[] key, final T val) {
        node.add(key, val);
    }

    @Override
    public void bake() {
        // fMap = new UnmodifiableTrie<byte[], Marker>(new PatriciaTrie(map));
    }

    @Override
    public T get(final byte[] search) {
        return node.traverse(search);
    }
}
