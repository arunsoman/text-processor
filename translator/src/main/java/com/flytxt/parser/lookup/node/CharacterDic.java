package com.flytxt.parser.lookup.node;

import java.util.HashMap;
import java.util.Map;

public class CharacterDic<T> {

    Map<Character, CharacterNode<T>> charList = new HashMap<>();

    public CharacterNode<T> getCharNode(final byte aChar) {
        CharacterNode<T> node;
        if (charList.containsKey((char) aChar)) {
            node = charList.get((char) aChar);
        } else {
            node = new CharacterNode<>((char) aChar);
            charList.put((char) aChar, node);
        }
        return node;
    }
}
