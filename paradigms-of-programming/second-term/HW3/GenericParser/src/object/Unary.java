package object;

import expression.TripleExpression;
import types.Types;


public abstract class Unary<T> implements TripleExpression<T> {
    private final TripleExpression<T> object;

    public Unary(TripleExpression<T> object) {
        this.object = object;
    }

    protected abstract T calculate(T x, Types<T> op);

    public T evaluate(T x, T y, T z, Types<T> op) {
        return calculate(object.evaluate(x, y, z, op), op);
    }
}
