package operation;

import expression.TripleExpression;
import types.Types;

public abstract class Binary<T> implements TripleExpression<T> {
    private final TripleExpression<T> firstObject;
    private final TripleExpression<T> secondObject;

    public Binary(TripleExpression<T> firstObject, TripleExpression<T> secondObject) {
        this.firstObject = firstObject;
        this.secondObject = secondObject;
    }

    protected abstract T calculate(T x, T y, Types<T> op);

    public T evaluate(T x, T y, T z, Types<T> op) {
        return calculate(firstObject.evaluate(x, y, z, op), secondObject.evaluate(x, y, z, op), op);
    }
}
