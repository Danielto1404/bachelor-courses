package exceptions;

public class MissingParenthesis extends FormatParserException {
    public MissingParenthesis(String status, int pos, String expression) {
        super("Can't find " + status + " parenthesis at index " + pos + "\n" + expression + mark(pos));
    }
}
