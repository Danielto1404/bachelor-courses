/*
import java.io.*;

public class FastScanner {
    InputStream in;
    int curPos, curSize;

    public FastScanner(InputStream input) {
        in = input;
    }

    public FastScanner(File file) throws IOException {
        in = new FileInputStream(file);
    }

    private int size = 256;
    private byte[] buffer = new byte[size];


    private void readBytes() throws IOException {
        curSize = in.read(buffer, 0, size);
        curPos = 0;
    }

    public boolean hasNextLine() throws IOException {
        if (curSize <= curPos) {
            readBytes();
        }
        return curSize > curPos;
    }

    public String nextLine() throws IOException {
        if (curPos == size) {
            readBytes();
        }
        StringBuilder line = new StringBuilder();
        while (curPos < curSize && !((buffer[curPos] == '\n'))) {
            char c = (char) buffer[curPos];
            line.append(c);
            curPos++;
            if (curPos == curSize) {
                readBytes();
            }
        }
        curPos++;
        return line.toString();
    }


    public static void main(String[] args) throws IOException {
        File file = new File("/Users/daniilkorolev/Documents/English_Advices.txt");
        FastScanner scanner = new FastScanner(file);
        for (int i = 0; i < 5; i++) {
            System.out.print(scanner.nextLine() + "    ");
        }
    }
}


*/
