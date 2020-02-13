package operation.checked;

import exceptions.ArithmeticParserException;
import expression.TripleExpression;
import operation.AbstractBinaryOperation;

public class CheckedAdd extends AbstractBinaryOperation {
    public CheckedAdd(TripleExpression left, TripleExpression right) {
        super(left, right);
    }

    protected int calculate(int left, int right) throws ArithmeticParserException {
        if (right > 0 ? left > Integer.MAX_VALUE - right
                : left < Integer.MIN_VALUE - right) {
            throw new ArithmeticParserException("Overflow exception : " + left + " + " + right);
        }
        return left + right;
    }
}
