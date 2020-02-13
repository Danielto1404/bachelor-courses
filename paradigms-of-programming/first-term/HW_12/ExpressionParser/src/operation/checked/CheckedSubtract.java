package operation.checked;

import exceptions.ArithmeticParserException;
import expression.TripleExpression;
import operation.AbstractBinaryOperation;

public class CheckedSubtract extends AbstractBinaryOperation {
    public CheckedSubtract(TripleExpression left, TripleExpression right) {
        super(left, right);
    }

    protected int calculate(int left, int right) throws ArithmeticParserException {
        if (right > 0 ? left < Integer.MIN_VALUE + right
                : left > Integer.MAX_VALUE + right) {
            throw new ArithmeticParserException("Integer overflow");
        }
        return left - right;
    }
}
