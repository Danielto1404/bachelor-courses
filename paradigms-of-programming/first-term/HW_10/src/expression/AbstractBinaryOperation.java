package expression;

public abstract class AbstractBinaryOperation implements NewExpression {
    private NewExpression left, right;

    AbstractBinaryOperation(NewExpression newLeft, NewExpression newRight) {
        left = newLeft;
        right = newRight;
    }

    abstract protected double calculate(double left, double right);

    abstract protected int calculate(int left, int right);

    public int evaluate(int x, int y, int z) {
        return calculate(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }

    public int evaluate(int x) {
        return calculate(left.evaluate(x), right.evaluate(x));
    }

    public strictfp double evaluate(double x) {
        return calculate(left.evaluate(x), right.evaluate(x));
    }
}
