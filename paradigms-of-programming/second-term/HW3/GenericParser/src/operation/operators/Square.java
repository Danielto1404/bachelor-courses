package operation.operators;

import expression.TripleExpression;
import object.Unary;
import types.Types;

public class Square<T> extends Unary<T> {
    public Square(TripleExpression<T> object) {
        super(object);
    }

    protected T calculate(T object, Types<T> op) {
        return op.square(object);
    }
}
