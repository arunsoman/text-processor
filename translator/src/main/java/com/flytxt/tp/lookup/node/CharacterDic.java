package com.flytxt.tp.lookup.node;

import java.util.HashMap;
import java.util.Map;

public class CharacterDic<T> {

    Map<Character, CharacterNode<T>> charList = new HashMap<>();

    public CharacterNode<T> getCharNode(final byte aByte) {
        final char aChar = (char) aByte;
        CharacterNode<T> node;
        if ((node = charList.get(aChar)) == null) {
            node = new CharacterNode<>(aChar);
            charList.put(aChar, node);
        }
        return node;
    }
}
