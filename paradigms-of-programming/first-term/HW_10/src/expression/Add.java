package expression;

public class Add extends AbstractBinaryOperation {
    public Add(NewExpression newLeft, NewExpression newRight) {
        super(newLeft, newRight);
    }

    protected strictfp double calculate(double left, double right) {
        return left + right;
    }

    protected int calculate(int left, int right) {
        return left + right;
    }
}