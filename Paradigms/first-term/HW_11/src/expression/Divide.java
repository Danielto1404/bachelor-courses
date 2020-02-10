package expression;

public class Divide extends AbstractBinaryOperation {
    public Divide(TripleExpression firstOperand, TripleExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected int calculate(int x, int y) {
        return x / y;
    }
}
