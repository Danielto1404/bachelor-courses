package expression;

public class ShiftLeft extends AbstractBinaryOperation{
    public ShiftLeft(TripleExpression firstOperand, TripleExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected int calculate(int x, int y) {
        return x << y;
    }
}
