package expression;

public class Negate extends AbstractUnaryOperation {
    public Negate(TripleExpression operand) {
        super(operand);
    }

    protected int calculate(int x) {
        return -x;
    }
}
