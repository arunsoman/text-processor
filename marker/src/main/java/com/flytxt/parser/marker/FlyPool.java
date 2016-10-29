package com.flytxt.parser.marker;

public class FlyPool<T> {

    private Node<T> current;

    private Node<T> head;

    private int size;

    private static class Node<T> {
        T item;
        Node<T> next;
        
        Node(final T element, final Node<T> next) {
            this.item = element;
            this.next = next;
        }
    }

    public T add(final T element) {
        final Node<T> f = head;
        Node<T> newNode = new Node<T>(element, f);
        head = newNode;
        size++;
        return element;
    }

    public T peek() {
        if (current != null) {
            final Node<T> result = current;
            current = current.next;
            return result.item;
        }
        return null;
    }

    public void reset() {
        current = head;
    }

}
