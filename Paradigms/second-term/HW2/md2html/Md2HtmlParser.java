package md2html;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

class Md2HtmlParser {
    private PrintWriter output;
    private FileSource input;
    private int HEADER_COUNT;
    private String paragraph;
    private int curPos = 0;
    private String currentIdentifier;
    private int posAfterHeader;
    private Map<String, Integer> COUNT_TAGS = new HashMap<>();
    private Map<Character, String> uniqueSymbols = Map.of(
            '<', "&lt;",
            '>', "&gt;",
            '&', "&amp;",
            '\\', "",
            '#', "#"
    );
    private Map<String, String> TAGS = Map.of(
            "#", "h",
            "*", "em",
            "_", "em",
            "**", "strong",
            "__", "strong",
            "-", "-",
            "--", "s",
            "`", "code",
            "+", "+",
            "++", "u"
    );


    Md2HtmlParser(FileSource input, PrintWriter output) {
        this.input = input;
        this.output = output;
    }


    private void setHeader(String identifier) {
        HEADER_COUNT = identifier.length();
    }

    private boolean checkLength(String identifier, int length) {
        return identifier.length() == length;
    }

    private boolean test(final char c) {
        return getChar() == c;
    }

    private boolean testNext(final char c) {
        char next = curPos < paragraph.length() - 1 ? paragraph.charAt(curPos + 1) : ' ';
        return next == c;
    }

    private boolean testPrev() {
        char prev = curPos > 0 ? paragraph.charAt(curPos - 1) : ' ';
        return prev == ' ';
    }

    private boolean testSingle() {
        return testPrev() && testNext(' ');
    }

    private char getChar() {
        return curPos < paragraph.length() ? paragraph.charAt(curPos) : FileSource.END;
    }

    private void next() {
        curPos++;
    }

    private boolean checkPos() {
        return curPos < paragraph.length();
    }

    private String createOpenTag(String tag, int... args) {
        String opened;
        if (TAGS.containsKey(tag)) {
            opened = args.length == 0 ? "<" + TAGS.get(tag) + ">" : "<" + TAGS.get(tag) + args[0] + ">";
        } else {
            opened = "<p>";
        }
        return opened;
    }

    private String createClosedTag(String tag, int... args) {
        String closed;
        if (TAGS.containsKey(tag)) {
            closed = args.length == 0 ? "</" + TAGS.get(tag) + ">" : "</" + TAGS.get(tag) + args[0] + ">";
        } else {
            closed = "</p>";
        }
        return closed;
    }

    private void setMap() {
        COUNT_TAGS.put("*", 0);
        COUNT_TAGS.put("_", 0);
        COUNT_TAGS.put("**", 0);
        COUNT_TAGS.put("__", 0);
        COUNT_TAGS.put("--", 0);
        COUNT_TAGS.put("`", 0);
        COUNT_TAGS.put("", 0);
        COUNT_TAGS.put("++", 0);
    }


    void parse() throws MdException {
        input.nextLine();
        input.skipEmptyLines();
        input.getNextParagraph();
        while (!input.getParagraph().equals(String.valueOf(FileSource.END))) {
            parseParagraph();
            input.getNextParagraph();
        }
        input.skipEmptyLines();
        output.close();
    }

    private void parseParagraph() {
        setDefault();
        StringBuilder value = new StringBuilder();
        String closingTag = HeaderOrParagraph(value);
        parseValue(value);
        output.println(value.append(closingTag));
    }

    private void setDefault() {
        paragraph = input.getParagraph();
        curPos = 0;
        posAfterHeader = 0;
        setMap();
        currentIdentifier = "";
    }

    private void checkStatus(StringBuilder value, String status) {
        if (status.equals("enable")) {
            value.append(parseString());
        } else {
            parseString();
        }
    }

    private String getNextIdentifier(StringBuilder value, String status) {
        checkStatus(value, status);
        if (!checkPos()) {
            currentIdentifier = "";
        } else {
            if (testNext(getChar())) {
                checkStatus(value, status);
                currentIdentifier = String.valueOf(getChar()) + String.valueOf(getChar());
                next();
                next();
            } else {
                if (getChar() == '-') {
                    if (status.equals("enable")) {
                        value.append('-');
                        next();
                        return getNextIdentifier(value, status);
                    }
                } else {
                    currentIdentifier = String.valueOf(getChar());
                }
                next();
            }
        }
        return currentIdentifier;
    }

    private String findFirstIdentifier(StringBuilder value) {
        while (currentIdentifier.isEmpty() && checkPos()) {
            getNextIdentifier(value, "disable");
        }
        return currentIdentifier;
    }

    private void countTags(StringBuilder value) {
        currentIdentifier = findFirstIdentifier(value);
        while (!currentIdentifier.isEmpty()) {
            try {
                int curValue = COUNT_TAGS.get(currentIdentifier);
                int newValue = curValue == 0 ? 1 : curValue + 1;
                COUNT_TAGS.put(currentIdentifier, newValue);
                getNextIdentifier(value, "disable");
            } catch (Exception e) {
                System.out.println(currentIdentifier);
                break;
            }
        }
    }

    private void decCountValues(String key) {
        COUNT_TAGS.put(key, COUNT_TAGS.get(key) - 1);
    }

    private void makeOdd() {
        for (String key : COUNT_TAGS.keySet()) {
            if (COUNT_TAGS.get(key) % 2 != 0) {
                decCountValues(key);
            }
        }
    }

    private void printValues(StringBuilder value) {
        curPos = posAfterHeader;
        while (checkPos()) {
            int count = COUNT_TAGS.get(getNextIdentifier(value, "enable"));
            if (count > 0) {
                if (count % 2 == 0) {
                    value.append(createOpenTag(currentIdentifier));
                } else {
                    value.append(createClosedTag(currentIdentifier));
                }
                decCountValues(currentIdentifier);
            } else {
                value.append(currentIdentifier);
            }
        }
    }

    private void parseValue(StringBuilder value) {
        countTags(value);
        makeOdd();
        printValues(value);
    }

    private String HeaderOrParagraph(StringBuilder value) {
        if (paragraph.charAt(0) == '#') {
            String identifier = parseIdentifier();
            if (test(' ') && checkLength(identifier, curPos)) {
                setHeader(identifier);
                value.append(createOpenTag("#", HEADER_COUNT));
                next();
                posAfterHeader = curPos;                                // Skip first whitespace
                return createClosedTag("#", HEADER_COUNT);
            } else {
                value.append(createOpenTag("p")).append(identifier);
                posAfterHeader = identifier.length();
            }
        } else {
            value.append(createOpenTag("p"));
            posAfterHeader = 0;
        }
        return createClosedTag("");
    }

    private String parseString() {
        if (checkPos()) {
            StringBuilder text = new StringBuilder();
            while (checkPos() && !TAGS.containsKey(String.valueOf(getChar())) && !uniqueSymbols.containsKey(getChar())) {
                text.append(getChar());
                next();
            }
            if (uniqueSymbols.containsKey(getChar())) {
                if (getChar() == '\\') {
                    next();
                    if (checkPos()) {
                        text.append(getChar());
                    } else {
                        text.append('\\');
                    }
                } else {
                    text.append(uniqueSymbols.get(getChar()));
                }
                next();
            }
            if (!TAGS.containsKey(String.valueOf(getChar())) || getChar() == '#') {
                text.append(parseString());
            }
            if (testSingle()) {
                text.append(getChar());
                next();
                text.append(parseString());
            }
            return text.toString();
        }
        return "";
    }

    private String parseIdentifier() {
        StringBuilder identifier = new StringBuilder();
        while (curPos < paragraph.length() && test('#')) {
            identifier.append('#');
            next();
        }
        return identifier.toString();
    }
}