package com.flytxt.parser.lookup.node;

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
        int maxMatch = 0;
        CharPath<T> maxPath = null;
        int currentMatch;
        for (final CharPath<T> aPath : paths) {
            currentMatch = aPath.match(search);
            if (currentMatch > maxMatch) {
                maxMatch = currentMatch;
                maxPath = aPath;
            }
        }
        return maxPath.value;
    }
}
