package json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple JSON parser.
 * Unsupported features: escapes in strings.
 * Extra features: Java whitespace, identifier map keys.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class JsonParser {
    private final JsonSource source;

    public JsonParser(final JsonSource source) {
        this.source = source;
    }

    public Object parse() throws JsonException {
        source.nextChar();
        skipSpaces();
        final Object result = parseValue();
        skipSpaces();
        expect(JsonSource.END, "end of file");
        return result;
    }

    private Object parseValue() throws JsonException {
        if (test('n') || test('t') || test('f')) {
            return parseConst();
        }
        if (Character.isDigit(source.getChar()) || test('-')) {
            return parseNumber();
        }
        if (testNext('"')) {
            return parseString();
        }
        if (testNext('[')) {
            return parseArray();
        }
        if (testNext('{')) {
            return parseObject();
        } else {
            throw source.error("Expected value, found '%c'", source.getChar());
        }
    }

    private Map<String, Object> parseObject() throws JsonException {
        skipSpaces();
        if (testNext('}')) {
            return Map.of();
        }
        final Map<String, Object> result = new HashMap<>();
        while (true) {
            final String key;
            if (testNext('"')) {
                key = parseString();
            } else if (Character.isLetter(source.getChar())) {
                key = parseIdentifier();
            } else {
                // Exception thrown
                expect('a', "string or identifier");
                return null;
            }
            skipSpaces();
            expect(':', "':'");
            skipSpaces();
            result.put(key, parseValue());
            skipSpaces();
            if (testNext('}')) {
                return result;
            }
            expect(',', "',' or '}'");
            skipSpaces();
        }
    }

    private List<Object> parseArray() throws JsonException {
        skipSpaces();
        if (testNext(']')) {
            return List.of();
        }
        final List<Object> result = new ArrayList<>();
        while (true) {
            result.add(parseValue());
            skipSpaces();
            if (testNext(']')) {
                return result;
            }
            expect(',', "',' or ']'");
            skipSpaces();
        }
    }

    private String parseString() throws JsonException {
        final StringBuilder sb = new StringBuilder();
        while (!test('"') && !test(JsonSource.END)) {
            sb.append(source.getChar());
            source.nextChar();
        }
        expect('\"', "Unterminated string literal");
        return sb.toString();
    }

    private Object parseConst() throws JsonException {
        final String identifier = parseIdentifier();
        switch (identifier) {
            case "null":
                return null;
            case "false":
                return false;
            case "true":
                return true;
            default:
                throw source.error("Expected value, found %s", identifier);
        }
    }

    private Number parseNumber() throws JsonException {
        final StringBuilder sb = new StringBuilder();
        readDigits(sb);
        if (test('.')) {
            readDigits(sb);
        }
        if (testNext('e') || testNext('E')) {
            sb.append("e");
            readDigits(sb);
        }
        try {
            return Double.parseDouble(sb.toString());
        } catch (final NumberFormatException e) {
            throw source.error("Invalid number '%s'", sb);
        }
    }

    private void readDigits(final StringBuilder sb) throws JsonException {
        do {
            sb.append(source.getChar());
        } while (Character.isDigit(source.nextChar()));
    }

    private String parseIdentifier() throws JsonException {
        final StringBuilder sb = new StringBuilder();
        do {
            sb.append(source.getChar());
        } while (Character.isLetterOrDigit(source.nextChar()));
        return sb.toString();
    }

    private boolean testNext(final char c) throws JsonException {
        if (source.getChar() == c) {
            source.nextChar();
            return true;
        } else {
            return false;
        }
    }

    private boolean test(final char c) {
        return source.getChar() == c;
    }

    private void expect(final char expected, final String errorMessage) throws JsonException {
        final char actual = source.getChar();
        if (actual == expected) {
            if (expected != JsonSource.END) {
                source.nextChar();
            }
        } else if (actual != JsonSource.END) {
            throw source.error("Expected %s, found '%s' (%d)", errorMessage, actual, (int) actual);
        } else {
            throw source.error("Expected %s, found EOF", errorMessage);
        }
    }

    private void skipSpaces() throws JsonException {
        while (Character.isWhitespace(source.getChar())) {
            source.nextChar();
        }
    }
}
