package exceptions;

public class MissingArgumentException extends FormatParserException {
    public MissingArgumentException(int pos, String expression) {
        super("Can't find argument at index " + (pos + 1) + "\n" + expression + mark(pos));
    }
}
