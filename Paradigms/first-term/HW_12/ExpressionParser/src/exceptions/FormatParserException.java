package exceptions;

public class FormatParserException extends Exception {
    public FormatParserException(String s) {
        super(s);
    }

    static String mark(int pos) {
        StringBuilder sb = new StringBuilder("\n");
        for (int i = 0; i < pos; i++) {
            sb.append(" ");
        }
        sb.append("^");
        return sb.toString();
    }

    static String markFunction(String name) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            sb.append("~");
        }
        return sb.toString();
    }
}