package com.flytxt.parser.marker;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class FlyList<T> implements List<T> {

    private T[] array;

    private int size;

    @SuppressWarnings("unchecked")
    public void set(final int size) {
        this.size = 0;
        array = (T[]) new Object[size];
    }

    @Override
    public boolean add(final T element) {
        if (size < array.length) {
            array[size++] = element;
            return true;
        }
        return false;
    }

    @Override
    public T get(final int index) {
        if (index - 1 < size) {
            return array[index - 1];
        }
        return null;
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {

        return false;
    }

    @Override
    public boolean contains(final Object o) {

        return false;
    }

    @Override
    public Iterator<T> iterator() {

        return null;
    }

    @Override
    public Object[] toArray() {

        return null;
    }

    @SuppressWarnings("hiding")
    @Override
    public <T> T[] toArray(final T[] a) {

        return null;
    }

    @Override
    public boolean remove(final Object o) {

        return false;
    }

    @Override
    public boolean containsAll(final Collection<?> c) {

        return false;
    }

    @Override
    public boolean addAll(final Collection<? extends T> c) {

        return false;
    }

    @Override
    public boolean addAll(final int index, final Collection<? extends T> c) {

        return false;
    }

    @Override
    public boolean removeAll(final Collection<?> c) {

        return false;
    }

    @Override
    public boolean retainAll(final Collection<?> c) {

        return false;
    }

    @Override
    public T set(final int index, final T element) {

        return null;
    }

    @Override
    public void add(final int index, final T element) {

    }

    @Override
    public T remove(final int index) {

        return null;
    }

    @Override
    public int indexOf(final Object o) {

        return 0;
    }

    @Override
    public int lastIndexOf(final Object o) {

        return 0;
    }

    @Override
    public ListIterator<T> listIterator() {

        return null;
    }

    @Override
    public ListIterator<T> listIterator(final int index) {

        return null;
    }

    @Override
    public List<T> subList(final int fromIndex, final int toIndex) {

        return null;
    }

}
