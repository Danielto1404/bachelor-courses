package types;

import exceptions.ArithmeticParserException;

public interface Types<T> {

    T add(final T x, final T y);

    T subtract(final T x, final T y);

    T divide(final T x, final T y);

    T multiply(final T x, final T y);

    T min(final T x, final T y);

    T max(final T x, final T y);

    T mod(final T x, final T y);

    T abs(final T x);

    T negate(final T x);

    T square(final T x);

    T parse2Digit(final String str) throws ArithmeticParserException;
}
