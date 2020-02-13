package object;

import expression.TripleExpression;

public class Const implements TripleExpression {
    private Number value;

    public Const(Number value) {
        assert value != null : "ERROR: Value of const is NULL!!!";
        this.value = value;
    }

    public int evaluate(int x, int y, int z) {
        return value.intValue();
    }
}
