package com.flytxt.tp.lookup.node;

import java.util.ArrayList;
import java.util.List;

public class CharacterNode<T> {

    public final Character aChar;

    private final List<CharPath<T>> paths = new ArrayList<>();

    public CharacterNode(final Character aChar) {
        super();
        this.aChar = aChar;
    }

    public void addPath(final CharPath<T> charPath) {
        paths.add(charPath);
    }

    public T getValue(final byte[] search) {
        CharPath<T> maxPath = null;
        int currentMatch, maxMatch = 0;
        for (final CharPath<T> aPath : paths) {
            currentMatch = aPath.match(search);
            if (currentMatch > maxMatch) {
                maxMatch = currentMatch;
                maxPath = aPath;
            }
        }
        return (maxPath==null)?null:maxPath.value;
    }
}
