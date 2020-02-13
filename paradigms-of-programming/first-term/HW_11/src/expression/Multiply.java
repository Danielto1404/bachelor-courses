package expression;


public class Multiply extends AbstractBinaryOperation {
    public Multiply(TripleExpression firstOperand, TripleExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected int calculate(int x, int y) {
        return x * y;
    }
}
