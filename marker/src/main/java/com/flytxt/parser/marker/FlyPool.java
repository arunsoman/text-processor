package com.flytxt.parser.marker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope("prototype")
public class FlyPool<T> {

    private Node<T> current;

    private Node<T> head;

    private int size;

    @Autowired
    private ApplicationContext appContext;
    
    @Component
    @Scope("prototype")
    private static class Node<T> {

        T item;

        Node<T> next;

        void set(final T element, final Node<T> next) {
            this.item = element;
            this.next = next;
        }
    }

    public T add(final T element) {
        final Node<T> f = head;
        Node<T> newNode = appContext.getBean(Node.class);
        newNode.set(element, f);
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
