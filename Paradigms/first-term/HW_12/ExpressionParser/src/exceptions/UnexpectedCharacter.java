package exceptions;

public class UnexpectedCharacter extends FormatParserException {
    public UnexpectedCharacter(char c, int pos, String expression) {
        super("Unexpected character \'" + c + "\' at index " + (pos + 1)
                + "\n" + expression + mark(pos));
    }
}
