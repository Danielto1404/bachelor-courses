package operation.checked;

import expression.TripleExpression;
import operation.AbstractUnaryOperation;

public class Low extends AbstractUnaryOperation {
    public Low(TripleExpression object) {
        super(object);
    }

    @Override
    protected int calculate(int object) {
        return Integer.lowestOneBit(object);
    }
}