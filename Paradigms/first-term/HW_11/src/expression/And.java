package expression;

public class And extends AbstractBinaryOperation {
    public And(TripleExpression firstOperand, TripleExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected int calculate(int x, int y) {
        return x & y;
    }
}
