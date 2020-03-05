package ru.ifmo.rain.korolev.arrayset;

import java.util.*;


@SuppressWarnings("unused")
public class ArrayNavigableSet<E> extends AbstractSet<E> implements NavigableSet<E> {

    private final ReversibleList<E> elements;
    private final Comparator<? super E> comparator;

    private Comparator<? super E> validateComparator(Comparator<? super E> comparator) {
        if (Comparator.naturalOrder().equals(comparator)) {
            return null;
        } else {
            return comparator;
        }
    }

    public ArrayNavigableSet() {
        this(Collections.emptyList(), null);
    }

    public ArrayNavigableSet(Collection<E> collection) {
        this(collection, null);
    }

    public ArrayNavigableSet(Comparator<? super E> comparator) {
        this(Collections.emptyList(), comparator);
    }

    public ArrayNavigableSet(Collection<? extends E> collection, Comparator<? super E> comparator) {
        TreeSet<E> treeSet = new TreeSet<>(comparator);
        treeSet.addAll(collection);
        this.elements = new ru.ifmo.rain.korolev.arrayset.ReversibleList<>(treeSet);
        this.comparator = validateComparator(comparator);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean contains(Object object) {
        return Collections.binarySearch(elements, (E) Objects.requireNonNull(object), comparator) >= 0;
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public Iterator<E> iterator() {
        return Collections.unmodifiableList(elements).iterator();
    }

    @Override
    public Comparator<? super E> comparator() {
        return comparator;
    }


    /**
     * Descending and subset operations
     */
    private ArrayNavigableSet(ReversibleList<E> reversibleArray, Comparator<? super E> comparator) {
        this.elements = reversibleArray;
        this.comparator = comparator;
    }

    @Override
    public NavigableSet<E> descendingSet() {
        return new ArrayNavigableSet<>(new ReversibleList<>(elements), Collections.reverseOrder(comparator));
    }

    @Override
    public Iterator<E> descendingIterator() {
        return descendingSet().iterator();
    }

    private NavigableSet<E> subSetImpl(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        Objects.requireNonNull(fromElement);
        Objects.requireNonNull(toElement);

        int fromIndex = index(fromElement, fromInclusive, false);
        int toIndex = index(toElement, toInclusive, true);

        if (fromIndex > toIndex) {
            return new ArrayNavigableSet<>(comparator);
        }
        return new ArrayNavigableSet<>(elements.subList(fromIndex, toIndex + 1), comparator);
    }

    @SuppressWarnings("unchecked")
    private int compare(E e1, E e2) {
        return (comparator == null) ? ((Comparable<E>) e1).compareTo(e2) : comparator.compare(e1, e2);
    }

    @Override
    public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        if (compare(fromElement, toElement) > 0) {
            throw new IllegalArgumentException();
        }
        return subSetImpl(fromElement, fromInclusive, toElement, toInclusive);
    }

    @Override
    public NavigableSet<E> headSet(E toElement, boolean inclusive) {
        if (isEmpty()) {
            return this;
        }
        return subSetImpl(first(), true, toElement, inclusive);
    }

    @Override
    public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
        if (isEmpty()) {
            return this;
        }
        return subSetImpl(fromElement, inclusive, last(), true);
    }

    @Override
    public SortedSet<E> subSet(E fromElement, E toElement) {
        return subSet(fromElement, true, toElement, false);
    }

    @Override
    public SortedSet<E> headSet(E toElement) {
        return headSet(toElement, false);
    }

    @Override
    public SortedSet<E> tailSet(E fromElement) {
        return tailSet(fromElement, true);
    }


    /**
     * lower, higher, ceiling, floor operations
     */
    private int index(E e, boolean inclusive, boolean lower) {
        int index = Collections.binarySearch(elements, e, comparator);
        if (index < 0) {
            // THAN NO SUCH ELEMENT IN COLLECTION
            int actualIndex = -(index + 1);
            return lower ? actualIndex - 1 : actualIndex;
        } else {
            return inclusive ? index : (lower ? (index - 1) : (index + 1));
        }
    }

    private E nullableGet(int index) {
        return (0 <= index && index < size()) ? elements.get(index) : null;
    }

    private E nullableGet(E e, boolean inclusive, boolean lower) {
        return nullableGet(index(e, inclusive, lower));
    }

    // Returns the largest element that is strictly less than the given argument
    @Override
    public E lower(E e) {
        return nullableGet(e, false, true);
    }

    // Returns the largest element that is less than or equal the given argument
    @Override
    public E floor(E e) {
        return nullableGet(e, true, true);
    }

    // Returns the smallest element that is strictly greater than the given argument
    @Override
    public E higher(E e) {
        return nullableGet(e, false, false);
    }

    // Returns the smallest element that is greater than or equal the given argument
    @Override
    public E ceiling(E e) {
        return nullableGet(e, true, false);
    }

    private void checkNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public E first() {
        checkNotEmpty();
        return elements.get(0);
    }

    @Override
    public E last() {
        checkNotEmpty();
        return elements.get(elements.size() - 1);
    }


    /**
     * Unsupported operations for unmodifiable collection
     */
    @Override
    public E pollFirst() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E pollLast() {
        throw new UnsupportedOperationException();
    }
}
