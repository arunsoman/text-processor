package com.flytxt.parser.lookup.node;

public class CharPath<T> {

    public final byte[] key;

    public final int charPos;

    public final T value;

    public CharPath(final byte[] key, final int charPos, final T value) {
        super();
        this.key = key;
        this.charPos = charPos;
        this.value = value;
    }

    public int match(final byte[] search) {
        if (search.length > key.length - charPos) {
            return -1;
        }
        int matchCnt = 0;
        for (final byte element : search) {
            if (key[charPos + matchCnt] == element) {
                matchCnt++;
            } else {
                return matchCnt;
            }
        }
        return matchCnt;
    }
}
