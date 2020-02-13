package operation.operators;

import expression.TripleExpression;
import operation.Binary;
import types.Types;

public class Divide<T> extends Binary<T> {
    public Divide(TripleExpression<T> firstObject, TripleExpression<T> secondObject) {
        super(firstObject, secondObject);
    }

    protected T calculate(T firstObject, T secondObject, Types<T> op) {
        return op.divide(firstObject, secondObject);
    }
}
