package expression;

public class Subtract extends AbstractBinaryOperation {
    public Subtract(TripleExpression firstOperand, TripleExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected int calculate(int x, int y) {
        return x - y;
    }
}
