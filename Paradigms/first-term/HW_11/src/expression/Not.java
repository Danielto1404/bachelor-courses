package expression;

public class Not extends AbstractUnaryOperation {
    public Not(TripleExpression operand) {
        super(operand);
    }

    protected int calculate(int x) {
        return ~x;
    }
}
