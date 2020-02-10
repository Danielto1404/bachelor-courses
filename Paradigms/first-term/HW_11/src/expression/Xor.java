package expression;

public class Xor extends AbstractBinaryOperation {
    public Xor(TripleExpression firstOperand, TripleExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected int calculate(int x, int y) {
        return x ^ y;
    }
}
