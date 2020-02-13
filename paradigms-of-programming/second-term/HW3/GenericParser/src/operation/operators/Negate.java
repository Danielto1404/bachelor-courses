package operation.operators;

import expression.TripleExpression;
import object.Unary;
import types.Types;

public class Negate<T> extends Unary<T> {
    public Negate(TripleExpression<T> object) {
        super(object);
    }

    protected T calculate(T object, Types<T> op) {
        return op.negate(object);
    }

}
