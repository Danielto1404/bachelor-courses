package operation.checked;

import exceptions.OverflowException;
import expression.TripleExpression;
import operation.AbstractBinaryOperation;


public class CheckedMultiply extends AbstractBinaryOperation {
    public CheckedMultiply(TripleExpression left, TripleExpression right) {
        super(left, right);
    }

    @Override
    protected int calculate(int left, int right) throws OverflowException {
        check(left, right);
        return left * right;
    }

    private void check(final int left, final int right) throws OverflowException {
        if (left > 0 && right > 0 && Integer.MAX_VALUE / left < right) {
            throw new OverflowException();
        }
        if (left > 0 && right < 0 && Integer.MIN_VALUE / left > right) {
            throw new OverflowException();
        }
        if (left < 0 && right > 0 && Integer.MIN_VALUE / right > left) {
            throw new OverflowException();
        }
        if (left < 0 && right < 0 && Integer.MAX_VALUE / left > right) {
            throw new OverflowException();
        }
    }
}
