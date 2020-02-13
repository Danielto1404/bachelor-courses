package queue;

public class ArrayQueue extends AbstractQueue {
    final private int DEFAULT_SIZE = 5;
    private int capacity = DEFAULT_SIZE;
    private Object[] elements = new Object[DEFAULT_SIZE];
    private int head;
    private int tail;

    protected void clearImpl() {
        elements = new Object[DEFAULT_SIZE];
        head = 0;
        tail = 0;
    }

    protected void pushImpl(Object element) {
        if (size == 0) {
            enqueueImpl(element);
        } else {
            if (size == capacity) {
                ensureCapacity();
            }
            head = prev(head);
            elements[head] = element;
        }
    }

    protected Object peekImpl() {
        return elements[tail];
    }

    protected Object removeImpl() {
        Object res = peekImpl();
        tail = prev(tail);
        return res;
    }

    protected Object elementImpl() {
        return elements[head];
    }

    protected void enqueueImpl(Object element) {
        if (size == 0) {
            head = 0;
            tail = 0;
            elements[head] = element;
        } else {
            if (size == capacity) {
                ensureCapacity();
            }
            tail = next(tail);
            elements[tail] = element;
        }
    }

    protected void dequeueImpl() {
        head = next(head);
    }

    private int next(int i) {
        return (i + 1) % capacity;
    }

    private int prev(int i) {
        return (capacity + i - 1) % capacity;
    }

    private void ensureCapacity() {
        Object[] copyArray = new Object[2 * capacity];
        if (size() < capacity) {
            return;
        } else {
            if (tail <= head) {
                System.arraycopy(elements, head, copyArray, 0, capacity - head);
                System.arraycopy(elements, 0, copyArray, capacity - head, tail + 1);
            } else {
                if (tail + 1 - head >= 0) System.arraycopy(elements, head, copyArray, head, tail + 1 - head);
            }
        }
        capacity *= 2;
        elements = copyArray;
        head = 0;
        tail = size - 1;
    }

    protected Queue typeQueue() {
        return new ArrayQueue();
    }

}



