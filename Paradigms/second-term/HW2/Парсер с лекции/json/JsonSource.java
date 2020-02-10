package json;

import java.io.IOException;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public abstract class JsonSource {
    public static char END = '\0';

    protected int pos;
    protected int line = 1;
    protected int posInLine;
    private char c;

    protected abstract char readChar() throws IOException;

    public char getChar() {
        return c;
    }

    public char nextChar() throws JsonException {
        try {
            if (c == '\n') {
                line++;
                posInLine = 0;
            }
            c = readChar();
            pos++;
            posInLine++;
            return c;
        } catch (final IOException e) {
            throw error("Source read error", e.getMessage());
        }
    }

    public JsonException error(final String format, final Object... args) throws JsonException {
        return new JsonException(line, posInLine, String.format("%d:%d: %s", line, posInLine, String.format(format, args)));
    }
}
