package queue;

import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractQueue implements Queue {
    protected int size;


    // POST: size = 0
    public void clear() {
        assert size > 0;
        size = 0;
        clearImpl();
    }

    // POST: ℝ = (size == 0)
    public boolean isEmpty() {
        return size == 0;
    }

    protected abstract void pushImpl(Object element);

    // PRE: element != null
    // POST: head' = prev(head) || head'.next = head ∧ head.value = element
    public void push(Object element) {
        assert element != null;
        pushImpl(element);
        size++;
    }


    // PRE: size > 0
    // POST: ℝ = tail.value ∧ size' == size ∧ tail' == tail
    public Object peek() {
        assert size > 0;
        return peekImpl();
    }

    // PRE:  size > 0
    // POST: ℝ = tail.value ∧ size' = size - 1 ∧ tail = prev(tail)
    public Object remove() {
        assert size > 0;
        Object res = removeImpl();
        size--;
        return res;
    }


    // PRE: size > 0 ∧ element in {queue} : element != null
    // POST: ℝ = head.value ∧ size' == size ∧ head' == head
    public Object element() {
        assert size > 0;
        return elementImpl();
    }

    // POST: ℝ = size
    public int size() {
        return size;
    }

    // PRE: element != null
    // POST: size' = size + 1 ∧ tail.value = element
    public void enqueue(Object element) {
        assert element != null;
        enqueueImpl(element);
        size++;
    }


    // PRE: size > 0
    // POST: ℝ = head.value ∧ size' = size - 1 ∧ head' = next(head)
    public Object dequeue() {
        assert size > 0;
        Object res = elementImpl();
        dequeueImpl();
        size--;
        return res;
    }

    public final Object[] toArray() {
        Object[] array = new Object[size()];
        for (int i = 0; i < size; i++) {
            array[i] = dequeue();
            enqueue(array[i]);
        }
        return array;
    }

    public Queue filter(Predicate<Object> predicate) {
        Object[] array = toArray();
        Queue filtered = typeQueue();
        for (int i = 0; i < size; i++) {
            if (predicate.test(array[i])) {
                filtered.enqueue(array[i]);
            }
        }
        return filtered;
    }

    public Queue map(Function<Object, Object> function) {
        Object[] array = toArray();
        Queue mapped = typeQueue();
        for (int i = 0; i < size; i++) {
            mapped.enqueue(function.apply(array[i]));
        }
        return mapped;
    }

    public Queue makeCopy() {
        Object[] toArray = toArray();
        final Queue copiedQueue = typeQueue();
        for (int i = 0; i < size(); i++) {
            copiedQueue.enqueue(toArray[i]);
        }
        return copiedQueue;
    }


    protected abstract void dequeueImpl();

    protected abstract void enqueueImpl(Object element);

    protected abstract Object elementImpl();

    protected abstract Object peekImpl();

    protected abstract void clearImpl();

    protected abstract Object removeImpl();

    protected abstract Queue typeQueue();
}


