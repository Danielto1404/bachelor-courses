package types;

import exceptions.ArithmeticParserException;

;

public class TypeShort implements Types<Short> {

    public Short add(final Short x, final Short y) {
        return (short) (x + y);
    }

    public Short subtract(final Short x, final Short y) {
        return (short) (x - y);
    }

    public Short multiply(final Short x, final Short y) {
        return (short) (x * y);
    }

    public Short divide(final Short x, final Short y) {
        return (short) (x / y);
    }

    public Short min(final Short x, final Short y) {
        return (short) (Math.min(x, y));
    }

    public Short max(final Short x, final Short y) {
        return (short) (Math.max(x, y));
    }

    public Short mod(final Short x, final Short y) {
        return (short) (x % y);
    }

    public Short abs(final Short x) {
        return (short) Math.abs(x);
    }

    public Short negate(final Short x) {
        return (short) -x;
    }

    public Short square(final Short x) {
        return (short) (x * x);
    }

    public Short parse2Digit(String str) throws ArithmeticParserException {
        try {
            return (short) Integer.parseInt(str);
        } catch (Exception e) {
            throw new ArithmeticParserException("Can't parse " + str + " to Short");
        }
    }
}
