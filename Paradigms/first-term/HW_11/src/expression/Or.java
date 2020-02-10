package expression;

public class Or extends AbstractBinaryOperation {
    public Or(TripleExpression firstOperand, TripleExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected int calculate(int x, int y) {
        return x | y;
    }
}
