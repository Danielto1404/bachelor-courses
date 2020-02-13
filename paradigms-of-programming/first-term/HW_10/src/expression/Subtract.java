package expression;

public class Subtract extends AbstractBinaryOperation {
    public Subtract(NewExpression newLeft, NewExpression newRight) {
        super(newLeft, newRight);
    }

    protected int calculate(int left, int right) {
        return left - right;
    }

    protected strictfp double calculate(double left, double right) {
        return left - right;
    }
}