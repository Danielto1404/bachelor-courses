package exceptions;

public class UnknownFunctionName extends FormatParserException {
    public UnknownFunctionName(String name, int pos, String expression) {
        super("found unknown lexeme : " + name + " at index " + pos + "\n" + expression +
                "\n" + markFunction(name) + "\n");
    }

}
