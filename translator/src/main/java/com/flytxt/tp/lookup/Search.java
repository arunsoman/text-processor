package com.flytxt.tp.lookup;

import java.io.File;

import com.flytxt.tp.lookup.node.CharPath;
import com.flytxt.tp.lookup.node.CharacterDic;
import com.flytxt.tp.marker.MarkerFactory;

public class Search<T> extends Lookup<T> {

    private final CharacterDic<T> dictionary = new CharacterDic<>();

    public Search(MarkerFactory mf) {
        this.mf = mf;
    }

    public Search(final String file) {
        this.fileName = file;
        loadFromFile();
    }

    public Search(final File file, MarkerFactory mf) {
        this.fileName = file.getAbsolutePath();
        this.mf = mf;
        loadFromFile();
    }

    @Override
    public void load(final byte[] key, final T val) {
        for (int i = 0; i < key.length; i++) {
            final CharPath<T> path = new CharPath<>(key, i, val);
            dictionary.getCharNode(key[i]).addPath(path);
        }
    }

    @Override
    public void bake() {
    }

    @Override
    public T get(final byte[] key) {
        return dictionary.getCharNode(key[0]).getValue(key);
    }
}
