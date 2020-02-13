package operation.operators;

import expression.TripleExpression;
import operation.Binary;
import types.Types;

public class Multiply<T> extends Binary<T> {

    public Multiply(TripleExpression<T> firstObject, TripleExpression<T> secondObject) {
        super(firstObject, secondObject);
    }

    protected T calculate(T firstObject, T secondObject, Types<T> op) {
        return op.multiply(firstObject, secondObject);
    }

}
