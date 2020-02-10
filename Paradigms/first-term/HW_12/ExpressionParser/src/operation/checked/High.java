package operation.checked;

import expression.TripleExpression;
import operation.AbstractUnaryOperation;

public class High extends AbstractUnaryOperation {
    public High(TripleExpression object) {
        super(object);
    }

    @Override
    protected int calculate(int object) {
        return Integer.highestOneBit(object);
    }
}
