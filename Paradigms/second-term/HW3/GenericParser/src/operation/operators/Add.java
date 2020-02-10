package operation.operators;

import expression.TripleExpression;
import operation.Binary;
import types.Types;

public class Add<T> extends Binary<T> {
    public Add(TripleExpression<T> firstObject, TripleExpression<T> secondObject) {
        super(firstObject, secondObject);
    }

    protected T calculate(T firstObject, T secondObject, Types<T> op) {
        return op.add(firstObject, secondObject);
    }
}
