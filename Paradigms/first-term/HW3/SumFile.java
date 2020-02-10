import java.io.*;
import java.util.Scanner;

public class SumFile {
    public static void main(String[] args) throws IOException {
        int sum = 0;
        File InFile = new File(args[0]);
        Scanner scanner = new Scanner(InFile);
        while (scanner.hasNextLine()) {
            for (String cur_number : scanner.nextLine().split("[^0-9-]")) {
                if (!cur_number.isEmpty()) {
                    sum += Integer.parseInt(cur_number);
                }
            }
        }
        File OutFile = new File(args[1]);
        FileWriter writer = new FileWriter(OutFile);
        writer.write(String.valueOf(sum));
        writer.close();
    }
}