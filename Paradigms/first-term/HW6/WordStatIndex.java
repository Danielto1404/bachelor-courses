/*
import java.util.LinkedHashMap;
import java.io.*;

public class WordStatIndex {
    public static void main(String[] args) throws IOException {
        LinkedHashMap<String, Integer> wordEntry = new LinkedHashMap<>();
        LinkedHashMap<String, StringBuilder> wordIndex = new LinkedHashMap<>();
        final int INITIAL_ENTRY = 1;
        final int INITIAL_INDEX = 0;
        int index = INITIAL_INDEX;
        try (FileBufferedReader reader = new FileBufferedReader(args[0])) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                for (String curLine : line.toLowerCase().split("[^\\p{L}\\p{Pd}\\']")) {
                    if (!curLine.isEmpty()) {
                        index++;
                        if (wordEntry.containsKey(curLine)) {
                            wordEntry.put(curLine, wordEntry.get(curLine) + 1);
                            wordIndex.put(curLine, wordIndex.get(curLine).append(" " + index));
                        } else {
                            wordEntry.put(curLine, INITIAL_ENTRY);
                            wordIndex.put(curLine, new StringBuilder(" " + index));
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(" Usage : <inputFile> <outputFile> ");
        } finally {
            PrintWriter writer = new PrintWriter(args[1], "UTF-8");
            for (String keys : wordEntry.keySet()) {
                writer.print(keys + " " + wordEntry.get(keys) + wordIndex.get(keys) + "\n");
            }
            writer.close();
        }
    }

    public static class FileBufferedReader implements AutoCloseable {
        public InputStreamReader in;
        private int size = 256;
        private char[] buffer = new char[size];
        private int curPos, curSize;

        public FileBufferedReader(String fileName) throws IOException {
            in = new InputStreamReader(new FileInputStream(fileName), "utf-8");
        }

        public void readChars() throws IOException {
            curSize = in.read(buffer);
            curPos = 0;
        }

        public String nextLine() throws IOException {
            if (curPos == curSize) {
                readChars();
            }
            StringBuilder word = new StringBuilder();
            while (curPos < curSize && (buffer[curPos] != '\n')) {
                word.append(buffer[curPos]);
                curPos++;
                if (curPos == curSize) {
                    readChars();
                }
            }
            curPos++;
            return word.toString();
        }

        public boolean hasNextLine() throws IOException {
            if (curPos == curSize) {
                readChars();
            }
            return curSize > curPos;
        }

        public void close() throws IOException {
            in.close();
        }
    }
}
*/
