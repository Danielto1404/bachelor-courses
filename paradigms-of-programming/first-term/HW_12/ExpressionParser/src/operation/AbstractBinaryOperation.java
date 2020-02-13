package operation;

import exceptions.ArithmeticParserException;
import exceptions.FormatParserException;
import expression.TripleExpression;

public abstract class AbstractBinaryOperation implements TripleExpression {
    private final TripleExpression left;
    private final TripleExpression right;

    public AbstractBinaryOperation(TripleExpression left, TripleExpression right) {
        this.left = left;
        this.right = right;
    }

    protected abstract int calculate(int left, int right);


    public int evaluate(int x, int y, int z) throws ArithmeticParserException {
        return calculate(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }
}
