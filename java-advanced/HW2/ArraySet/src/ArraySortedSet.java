import java.util.*;

import static java.lang.Math.abs;

public class ArraySortedSet<E> extends AbstractSet<E> implements SortedSet<E> {

    private final List<E> elements;
    private final Comparator<? super E> comparator;

    public ArraySortedSet() {
        this.elements = Collections.emptyList();
        comparator = null;
    }

    public ArraySortedSet(Collection<? extends E> collection) {
        this(collection, null);
    }

    public ArraySortedSet(Collection<? extends E> collection, Comparator<? super E> comparator) {
        this.comparator = comparator;
        TreeSet<E> treeSet = new TreeSet<>(comparator);
        treeSet.addAll(collection);
        elements = new ArrayList<>(treeSet);
    }

    // AbstractSet methods
    @Override
    public Iterator<E> iterator() {
        return Collections.unmodifiableList(elements).iterator();
    }

    @Override
    public int size() {
        return elements.size();
    }

    // SortedSet methods
    @Override
    public Comparator<? super E> comparator() {
        return comparator;
    }

    @Override
    public SortedSet<E> subSet(E from, E to) {
        return null;
    }

    @Override
    public SortedSet<E> headSet(E to) {
        return null;
    }

    @Override
    public SortedSet<E> tailSet(E from) {
        return ;
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

    private void checkNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    private int findCorrectIndex(E e) {
        int index = Collections.binarySearch(elements, e, comparator);
        return index >= 0 ? index : abs(index - 1);
    }
}