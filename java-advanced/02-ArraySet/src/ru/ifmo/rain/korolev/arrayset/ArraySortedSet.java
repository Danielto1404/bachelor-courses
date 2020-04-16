package ru.ifmo.rain.korolev.arrayset;

import java.util.*;

public class ArraySortedSet<E> extends AbstractSet<E> implements SortedSet<E> {

    private final List<E> elements;
    private final Comparator<? super E> comparator;

    public ArraySortedSet() {
        this.elements = Collections.emptyList();
        this.comparator = null;
    }

    public ArraySortedSet(Collection<? extends E> elements) {
        this(elements, null);
    }

    public ArraySortedSet(Comparator<? super E> comparator) {
        this(Collections.emptyList(), comparator);
    }

    public ArraySortedSet(Collection<? extends E> collection, Comparator<? super E> comparator) {
        TreeSet<E> treeSet = new TreeSet<>(comparator);
        treeSet.addAll(collection);
        this.elements = new ArrayList<>(treeSet);
        this.comparator = comparator;
    }

    private ArraySortedSet(List<E> elements, Comparator<? super E> comparator) {
        this.elements = elements;
        this.comparator = comparator;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean contains(Object e) {
        return Collections.binarySearch(elements, (E) Objects.requireNonNull(e), comparator) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        return Collections.unmodifiableList(elements).iterator();
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public Comparator<? super E> comparator() {
        return comparator;
    }

    @Override
    public SortedSet<E> subSet(E from, E to) {
        if (compare(from, to) > 0) {
            throw new IllegalArgumentException();
        }
        return headSet(to).tailSet(from);
    }

    @Override
    public SortedSet<E> headSet(E e) {
        return new ArraySortedSet<>(elements.subList(0, index(e)), comparator);
    }

    @Override
    public SortedSet<E> tailSet(E e) {
        return new ArraySortedSet<>(elements.subList(index(e), size()), comparator);
    }

    @Override
    public E first() {
        checkNotEmpty();
        return elements.get(0);
    }

    @Override
    public E last() {
        checkNotEmpty();
        return elements.get(size() - 1);
    }

    @SuppressWarnings("unchecked")
    private int compare(E a, E b) {
        return comparator == null ? ((Comparable<E>) a).compareTo(b) : comparator.compare(a, b);
    }

    private int index(E e) {
        int index = Collections.binarySearch(elements, e, comparator);
        return index >= 0 ? index : -index - 1;
    }

    private void checkNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }
}