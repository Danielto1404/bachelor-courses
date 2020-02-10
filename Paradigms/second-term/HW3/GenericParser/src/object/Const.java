package object;

import expression.TripleExpression;
import types.Types;


public class Const<T> implements TripleExpression<T> {
    private final T value;

    public Const(T value) {
        this.value = value;
    }

    public T evaluate(T x, T y, T z, Types<T> op) {
        return value;
    }
}
