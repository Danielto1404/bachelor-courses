package types;

import exceptions.ArithmeticParserException;
import exceptions.IllegalOperationException;
import exceptions.OverflowException;

public class TypeInteger implements Types<Integer> {
    private final boolean checked;

    public TypeInteger(final boolean check) {
        checked = check;
    }

    private void checkAdd(final Integer x, final Integer y) throws OverflowException {
        if (x > 0 && Integer.MAX_VALUE - x < y) {
            throw new OverflowException();
        }
        if (x < 0 && Integer.MIN_VALUE - x > y) {
            throw new OverflowException();
        }
    }

    public Integer add(final Integer x, final Integer y) throws OverflowException {
        if (checked) {
            checkAdd(x, y);
        }
        return x + y;
    }

    private void checkSub(final Integer x, final Integer y) throws OverflowException {
        if (x >= 0 && y < 0 && x - Integer.MAX_VALUE > y) {
            throw new OverflowException();
        }
        if (x <= 0 && y > 0 && Integer.MIN_VALUE - x > -y) {
            throw new OverflowException();
        }
    }

    public Integer subtract(final Integer x, final Integer y) throws OverflowException {
        if (checked) {
            checkSub(x, y);
        }
        return x - y;
    }

    private void checkMul(final Integer x, final Integer y) throws OverflowException {
        if (x > 0 && y > 0 && Integer.MAX_VALUE / x < y) {
            throw new OverflowException();
        }
        if (x > 0 && y < 0 && Integer.MIN_VALUE / x > y) {
            throw new OverflowException();
        }
        if (x < 0 && y > 0 && Integer.MIN_VALUE / y > x) {
            throw new OverflowException();
        }
        if (x < 0 && y < 0 && Integer.MAX_VALUE / x > y) {
            throw new OverflowException();
        }
    }

    public Integer multiply(final Integer x, final Integer y) throws OverflowException {
        if (checked) {
            checkMul(x, y);
        }
        return x * y;
    }

    @Override
    public Integer min(Integer x, Integer y) {
        return null;
    }

    @Override
    public Integer max(Integer x, Integer y) {
        return null;
    }

    private void checkDiv(final Integer x, final Integer y) throws OverflowException {
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new OverflowException();
        }
    }

    private void checkZero(final int y, final String reason) throws IllegalOperationException {
        if (y == 0) {
            throw new IllegalOperationException(reason);
        }
    }

    public Integer divide(final Integer x, final Integer y) throws IllegalOperationException, OverflowException {
        checkZero(y, "Division by zero");
        if (checked) {
            checkDiv(x, y);
        }
        return x / y;
    }

    public Integer mod(final Integer x, final Integer y) throws IllegalOperationException {
        checkZero(y, "Taking module by zero");
        return x % y;
    }

    private void checkNot(final Integer x) throws OverflowException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    public Integer negate(final Integer x) throws OverflowException {
        if (checked) {
            checkNot(x);
        }
        return -x;
    }

    public Integer square(Integer x) {
        if (checked) {
            checkMul(x, x);
        }
        return x * x;
    }

    private void checkAbs(final Integer x) throws OverflowException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    public Integer abs(final Integer x) throws OverflowException {
        if (checked) {
            checkAbs(x);
        }
        return Math.abs(x);
    }

    public Integer parse2Digit(String str) throws ArithmeticParserException {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            throw new ArithmeticParserException("Can't parse " + str + " to Integer");
        }
    }
}
