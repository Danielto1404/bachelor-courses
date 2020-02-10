import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileBufferedReader implements AutoCloseable {
    private final InputStreamReader in;
    private final char NEW_LINE = '\n';
    private final char APOSTROPHE = '\'';
    private final char DASH = Character.DASH_PUNCTUATION;
    private final int size = 256;
    public char[] buffer = new char[size];
    public int curPos, curSize;

    FileBufferedReader(String fileName) throws IOException {
        in = new InputStreamReader(new FileInputStream(fileName), "utf-8");
    }

    private void readChars() throws IOException {
        curSize = in.read(buffer);
        curPos = 0;
    }

    boolean hasNext() throws IOException {
        if (curPos == curSize) {
            readChars();
        }
        return curSize > curPos;
    }

    public boolean thisLine() throws IOException {
        if (curSize == curPos) {
            readChars();
        }
        if (curSize == 0) return true;
        else return curPos != curSize && buffer[curPos] != NEW_LINE;
    }

    public String nextWord() throws IOException {
        if (curPos == curSize) {
            readChars();
        }
        StringBuilder currentWord = new StringBuilder();
        while (curPos < curSize && !charIsPartOfWord(buffer[curPos])) {
            curPos++;
            if (!thisLine()) {
                break;
            }
        }
        while (curPos < curSize && charIsPartOfWord(buffer[curPos])) {
            currentWord.append(buffer[curPos]);
            curPos++;
            if (curPos == curSize) {
                readChars();
            }
        }
        curPos++;
        return currentWord.toString().toLowerCase();
    }

    private boolean charIsPartOfWord(char symbol) {
        return Character.isLetter(symbol) || Character.getType(symbol) == DASH || symbol == APOSTROPHE;
    }

    @Override
    public void close() throws IOException {
        in.close();
    }

    public static void main(String[] args) throws IOException {
        FileBufferedReader reader = new FileBufferedReader("/Users/daniilkorolev/Documents/test.txt");
        while (reader.hasNext()) {
            while (!reader.thisLine()) {
                System.out.print(reader.nextWord() + " ");
            }
            System.out.println();
        }
    }
}