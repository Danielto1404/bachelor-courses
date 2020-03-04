package ru.ifmo.rain.korolev.arrayset;

import java.util.*;

public class ReversibleList<E> extends AbstractList<E> implements RandomAccess {
    private final List<E> elements;
    private boolean flip;

    private ReversibleList(List<E> list, boolean flip) {
        this.elements = Collections.unmodifiableList(list);
        this.flip = flip;
    }

    public ReversibleList(Collection<E> collection) {
        this.elements = List.copyOf(collection);
        this.flip = false;
    }

    public ReversibleList(ReversibleList<E> other) {
        this.elements = other.elements;
        this.flip = !other.flip;
    }

    private int index(int index) {
        return flip ? size() - 1 - index : index;
    }

    @Override
    public ReversibleList<E> subList(int fromIndex, int toIndex) {
        if (flip) {
            return new ReversibleList<>(elements.subList(index(toIndex - 1), index(fromIndex) + 1), flip);
        } else {
            return new ReversibleList<>(elements.subList(index(fromIndex), index(toIndex)), flip);
        }
    }

    @Override
    public E get(int index) {
        return elements.get(index(index));
    }

    @Override
    public int size() {
        return elements.size();
    }
}