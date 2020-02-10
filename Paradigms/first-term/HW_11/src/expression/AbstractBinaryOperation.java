package expression;

public abstract class AbstractBinaryOperation implements TripleExpression {
    private final TripleExpression firstOperand;
    private final TripleExpression secondOperand;

    public AbstractBinaryOperation(TripleExpression firstOperand, TripleExpression secondOperand) {
        assert firstOperand != null && secondOperand != null : "ERROR: One of the operands is null!";
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
    }

    protected abstract int calculate(int x, int y);

    public int evaluate(int x, int y, int z) {
        return calculate(firstOperand.evaluate(x, y, z), secondOperand.evaluate(x, y, z));
    }
}