package expression;

public abstract class AbstractUnaryOperation implements TripleExpression {
    private final TripleExpression operand;

    protected AbstractUnaryOperation(TripleExpression operand) {
        assert operand != null : "ERROR: Operand is null!";
        this.operand = operand;
    }

    protected abstract int calculate(int x);

    public int evaluate(int x, int y, int z) {
        return calculate(operand.evaluate(x, y, z));
    }
}
