package operation.operators;

import expression.TripleExpression;
import object.Unary;
import types.Types;

public class Abs<T> extends Unary<T> {
    public Abs(TripleExpression<T> object) {
        super(object);
    }

    protected T calculate(T object, Types<T> op) {
        return op.abs(object);
    }
}
