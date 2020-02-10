package expression;

public class Divide extends AbstractBinaryOperation {
    public Divide(NewExpression newLeft, NewExpression newRight) {
        super(newLeft, newRight);
    }

    protected int calculate(int left, int right) {
        return left / right;
    }

    protected strictfp double calculate(double left, double right) {
        return left / right;
    }
}