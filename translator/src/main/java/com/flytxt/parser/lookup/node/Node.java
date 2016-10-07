package com.flytxt.parser.lookup.node;

public class Node<T> {

    @SuppressWarnings("unchecked")
    public final Node<T>[] nodes = new Node[122];

    private boolean isLeafNode;

    private T value;

    public void add(final byte[] key, final T value) {
        Node<T> n = this;
        Node<T> pre = this;
        for (final int i : key) {
            pre = n;
            n = n.nodes[i];
            if (n == null) {
                n = new Node<>();
                pre.nodes[i] = n;
            }
        }
        n.isLeafNode = true;
        n.value = value;
    }

    public T traverse(final byte[] search) {
        Node<T> n = this;
        for (final int aChar : search) {
            n = n.nodes[aChar];
            if (n == null) {
                return null;
            }
            if (n.isLeafNode) {
                return value;
            }
        }
        return null;
    }

}
