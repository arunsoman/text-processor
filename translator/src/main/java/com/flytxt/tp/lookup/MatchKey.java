package com.flytxt.tp.lookup;

import java.io.File;

import com.flytxt.tp.lookup.node.Node;
import com.flytxt.tp.marker.MarkerFactory;

public class MatchKey<T> extends Lookup<T> {

    private final Node<T> node = new Node<>();

    public MatchKey(final MarkerFactory mf) {
        this.mf = mf;
    }

    public MatchKey(final String file) {
        this.fileName = file;
        loadFromFile();
    }

    public MatchKey(final File file, MarkerFactory mf) {
        this.fileName = file.getAbsolutePath();
        this.mf = mf;
        loadFromFile();
    }

    @Override
    public void load(final byte[] key, final T val) {
        node.add(key, val);
    }

    @Override
    public void bake() {
    }

    @Override
    public T get(final byte[] search) {
        return node.traverse(search);
    }

    @Override
    public String toString() {
        int i = 0;
        StringBuilder result = new StringBuilder();
        for (Node<T> b : this.node.nodes) {
            if (b != null)
                result.append((char) i + "  ");
            i++;
        }
        return result.toString();
    }

}
