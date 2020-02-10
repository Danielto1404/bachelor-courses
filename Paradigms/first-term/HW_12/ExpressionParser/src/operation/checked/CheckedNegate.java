package operation.checked;

import exceptions.ArithmeticParserException;
import expression.TripleExpression;
import operation.AbstractUnaryOperation;


public class CheckedNegate extends AbstractUnaryOperation {
    public CheckedNegate(TripleExpression object) {
        super(object);
    }

    @Override
    protected int calculate(int object) throws ArithmeticParserException {
        if (object == Integer.MIN_VALUE)
            throw new ArithmeticParserException("Overflow exception : -(" + object + ")");
        return -object;
    }

}
