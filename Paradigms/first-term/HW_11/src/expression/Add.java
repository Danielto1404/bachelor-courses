package expression;

public class Add extends AbstractBinaryOperation {
    public Add(TripleExpression firstOperand, TripleExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected int calculate(int x, int y) {
        return x + y;
    }
}
