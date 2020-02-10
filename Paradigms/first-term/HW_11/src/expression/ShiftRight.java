package expression;

public class ShiftRight extends AbstractBinaryOperation {
    public ShiftRight(TripleExpression firstOperand, TripleExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected int calculate(int x, int y) {
        return x >> y;
    }
}
