package types;

import exceptions.ArithmeticParserException;

public class TypeByte implements Types<Byte> {

    public Byte add(final Byte x, final Byte y) {
        return (byte) (x + y);
    }

    public Byte subtract(final Byte x, final Byte y) {
        return (byte) (x - y);
    }

    public Byte multiply(final Byte x, final Byte y) {
        return (byte) (x * y);
    }

    public Byte divide(final Byte x, final Byte y) {
        return (byte) (x / y);
    }

    public Byte min(final Byte x, final Byte y) {
        return (byte) (Math.min(x, y));
    }

    public Byte max(final Byte x, final Byte y) {
        return (byte) (Math.max(x, y));
    }

    public Byte mod(final Byte x, final Byte y) {
        return (byte) (x % y);
    }

    public Byte abs(final Byte x) {
        return (byte) Math.abs(x);
    }

    public Byte negate(final Byte x) {
        return (byte) -x;
    }

    public Byte square(final Byte x) {
        return (byte) (x * x);
    }

    public Byte parse2Digit(String str) throws ArithmeticParserException {
        try {
            return (byte) Integer.parseInt(str);
        } catch (Exception e) {
            throw new ArithmeticParserException("Can't parse " + str + " to Byte");
        }
    }
}
