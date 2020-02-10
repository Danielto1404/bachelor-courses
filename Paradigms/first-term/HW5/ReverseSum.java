import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ReverseSum {
    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        List<ArrayList<Integer>> matrix = new ArrayList<>();
        List<Integer> sumOfLines = new ArrayList<>();
        List<Integer> sumOfColumns = new ArrayList<>();
        try {
            while (scanner.hasNextLine()) {
                matrix.add(new ArrayList<>());
                String line = scanner.nextLine();
                String[] numbers = line.split(" ");
                sumOfLines.add(0);
                for (int j = 0; j < numbers.length; j++)
                    if (numbers[j].length() > 0) {
                        int cur = new Integer(numbers[j]);
                        matrix.get(matrix.size() - 1).add(cur);
                        sumOfLines.set(sumOfLines.size() - 1, sumOfLines.get(sumOfLines.size() - 1) + cur);
                        if (j >= sumOfColumns.size())
                            sumOfColumns.add(0);
                        sumOfColumns.set(j, sumOfColumns.get(j) + cur);
                    }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < matrix.size(); ++i) {
            for (int j = 0; j < matrix.get(i).size(); ++j) {
                System.out.print(sumOfLines.get(i) + sumOfColumns.get(j) - matrix.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    static class FastScanner {

        InputStream in;
        int curPos, curSize;

        public FastScanner(InputStream input) {
            in = input;
        }

        private int SIZE = 256;
        private byte[] buffer = new byte[SIZE];


        private void readBytes() throws IOException {
            curSize = in.read(buffer);
            curPos = 0;
        }

        public boolean hasNextLine() throws IOException {
            if (curSize == curPos) {
                readBytes();
            }
            return curSize > curPos;
        }

        public String nextLine() throws IOException {
            if (curPos == curSize) {
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
    }
}