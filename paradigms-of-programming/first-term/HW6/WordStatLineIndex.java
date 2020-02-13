import java.util.*;
import java.io.*;

public class WordStatLineIndex {
    final static int INITIAL_ONE = 1;
    final static int INITIAL_ZERO = 0;

    public static void main(String[] args) throws IOException {

        int lineIndex = INITIAL_ONE;
        int wordIndex;
        Map<String, Pair<Integer, StringBuilder>> wordCount = new TreeMap<>();
        try (FileBufferedReader reader = new FileBufferedReader(args[0])) {
            while (reader.hasNext()) {
                wordIndex = INITIAL_ZERO;
                while (reader.thisLine()) {
                    String curWord = reader.nextWord();
                    if (!curWord.isEmpty()) {
                        wordIndex++;
                        if (wordCount.containsKey(curWord)) {
                            wordCount.put(curWord,
                                    new Pair<>(wordCount.get(curWord).getFirst() + 1,
                                            wordCount.get(curWord).getSecond().
                                                    append(" " + lineIndex + ":" + wordIndex)));
                        } else {
                            wordCount.put(curWord, new Pair<>(INITIAL_ONE,
                                    new StringBuilder(" " + lineIndex + ":" + wordIndex)));
                        }
                    }
                }
                lineIndex++;
            }
        } catch (Exception e) {
            System.out.println("Usage java : <fileInputName> <fileOutPutName> ");
        } finally {
            PrintWriter writer = new PrintWriter(args[1], "utf-8");
            for (String keys : wordCount.keySet()) {
                writer.println(keys + " " + wordCount.get(keys).getFirst() + wordCount.get(keys).getSecond());
            }
            writer.close();
        }
    }

    static class FileBufferedReader implements AutoCloseable {
        private char currentSymbol;
        private final InputStreamReader in;
        private static final byte EOF = -1;
        private static final char NEW_LINE = '\n';
        private static final char APOSTROPHE = '\'';

        FileBufferedReader(String fileName) throws IOException {
            in = new InputStreamReader(new FileInputStream(fileName), "utf-8");
        }

        private void readNextChar() throws IOException {
            currentSymbol = (char) in.read();
        }

        boolean hasNext() throws IOException {
            readNextChar();
            return (byte) currentSymbol != EOF;
        }

        boolean thisLine() {
            return currentSymbol != NEW_LINE
                    && (byte) currentSymbol != EOF;
        }

        String nextWord() throws IOException {
            StringBuilder currentWord = new StringBuilder();
            while (!isPunctuation(currentSymbol)) {
                readNextChar();
                if (!thisLine()) {
                    break;
                }
            }
            while (isPunctuation(currentSymbol)) {
                currentWord.append(currentSymbol);
                readNextChar();
            }
            return currentWord.toString().toLowerCase();
        }

        private boolean isPunctuation(char symbol) {
            return Character.isLetter(symbol) || Character.getType(symbol) == Character.DASH_PUNCTUATION
                    || symbol == APOSTROPHE;
        }

        public void close() {
        }
    }

    public static class Pair<K, V> {
        public K first;
        public V second;

        public Pair(K first, V second) {
            this.first = first;
            this.second = second;
        }

        public K getFirst() {
            return first;
        }

        public V getSecond() {
            return second;
        }
    }
}


