package operation;

import expression.TripleExpression;

public abstract class AbstractUnaryOperation implements TripleExpression {
    private final TripleExpression operand;

    public AbstractUnaryOperation(final TripleExpression value) {
        operand = value;
    }

    protected abstract int calculate(int operand);

    public int evaluate(int x, int y, int z) {
        return calculate(operand.evaluate(x, y, z));
    }
}
