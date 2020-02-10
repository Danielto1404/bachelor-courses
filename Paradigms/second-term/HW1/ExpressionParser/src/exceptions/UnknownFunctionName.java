package exceptions;

public class UnknownFunctionName extends FormatParserException {
    public UnknownFunctionName(String name, int pos, String expression) {
        super("found unknown lexeme : " + name + " at index " + (pos + 1) + "\n" + expression +
                "\n" + markFunction(name) + "\n");
    }

}
