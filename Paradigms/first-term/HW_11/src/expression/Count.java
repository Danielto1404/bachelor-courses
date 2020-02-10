package expression;

public class Count extends AbstractUnaryOperation {
    public Count(TripleExpression operand) {
        super(operand);
    }

    protected int calculate(int x) {
        return Integer.bitCount(x);
    }
}
