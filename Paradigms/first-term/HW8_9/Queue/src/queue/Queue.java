package queue;

import java.util.function.Predicate;
import java.util.function.Function;

public interface Queue extends Copyable {
    void enqueue(Object object);

    Object dequeue();

    Object peek();

    Object element();

    Object remove();

    void push(Object object);

    boolean isEmpty();

    int size();

    void clear();

    Queue filter(Predicate<Object> predicate);

    Queue map(Function<Object, Object> function);

    Object[] toArray();
}
