package operation.checked;

import exceptions.*;
import expression.TripleExpression;
import operation.AbstractBinaryOperation;

public class CheckedDivide extends AbstractBinaryOperation {
    public CheckedDivide(TripleExpression left, TripleExpression right) {
        super(left, right);
    }

    private void check(int left, int right) {
        if (right == 0) {
            throw new IllegalOperationException("ERROR: Division by ZERO");
        }
        if (left == Integer.MIN_VALUE && right == -1) {
            throw new OverflowException();
        }
    }

    @Override
    protected int calculate(int left, int right) throws ArithmeticParserException {
        check(left, right);
        return left / right;
    }
}