package tabulator;


import exceptions.ArithmeticParserException;
import exceptions.FormatParserException;
import exceptions.UnsupportedTypeException;
import expression.TripleExpression;
import parser.ExpressionParser;
import types.*;

import java.util.Map;
import java.util.function.Function;

public class GenericTabulator implements Tabulator {
    private Map<String, Types<?>> MODES = Map.of(
            "i", new TypeInteger(true),
            "d", new TypeDouble(),
            "bi", new TypeBigInteger(),
            "u", new TypeInteger(false),
            "b", new TypeByte(),
            "f", new TypeFloat(),
            "s", new TypeShort(),
            "l", new TypeLong()
    );

    public Object[][][] tabulate(final String mode, final String exprStr, final int x1, final int x2, final int y1,
                                 final int y2, final int z1, final int z2) throws UnsupportedTypeException, FormatParserException {
        Types<?> type = MODES.get(mode);
        if (type != null) {
            return tabulate(type, exprStr, x1, x2, y1, y2, z1, z2);
        }
        throw new UnsupportedTypeException(mode);
    }

    private <T> Object[][][] tabulate(final Types<T> op, final String exprStr, final int x1, final int x2,
                                      final int y1, final int y2, final int z1, final int z2) throws ArithmeticParserException, FormatParserException {
        final TripleExpression<T> expression = new ExpressionParser<>(op).parse(exprStr);
        final Function<Integer, T> convert2Type = obj -> op.parse2Digit(Integer.toString(obj));
        final int dX = x2 - x1 + 1;
        final int dY = y2 - y1 + 1;
        final int dZ = z2 - z1 + 1;
        Object[][][] result = new Object[dX][dY][dZ];
        for (int x = 0; x < dX; x++) {
            for (int y = 0; y < dY; y++) {
                for (int z = 0; z < dZ; z++) {
                    try {
                        result[x][y][z] = expression.evaluate(convert2Type.apply(x + x1), convert2Type.apply(y + y1),
                                convert2Type.apply(z + z1), op);
                    } catch (ArithmeticException ignored) {
                    }
                }
            }
        }
        return result;
    }
}