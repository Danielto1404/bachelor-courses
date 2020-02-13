import java.io.*;

public class SumHexFile {
    public static void main(String[] args) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "UTF-8"));
            String AllElements = "";
            int Sum = 0;
            try {
                String Line;
                while ((Line = reader.readLine()) != null) {
                    AllElements += Line + " ";
                }
                for (String CurrentNumber : AllElements.toLowerCase().split("((^0x)+[^1-9a-f]+)")) {
                    if (!CurrentNumber.isEmpty()) {
                        if (CurrentNumber.substring(0, 2) == "0x") {
                            Sum += Long.parseLong(CurrentNumber, 16);
                        } else {
                            Sum += Long.parseLong(CurrentNumber, 10);
                        }

                    }
                }
            } catch (Exception e) {
            } finally {
                reader.close();
                try {
                    PrintWriter writer = new PrintWriter(args[1], "UTF-8");
                    writer.print(Sum);
                    writer.close();
                } catch (IOException e) {
                }
            }
        } catch (IOException e) {
            System.out.println("I feel sorry, can't find the problem");
        }
    }
}
