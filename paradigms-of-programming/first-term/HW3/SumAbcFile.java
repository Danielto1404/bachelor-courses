import java.io.*;

public class SumAbcFile {
    public static void main(String[] args) {
        BufferedReader reader;
        if (args.length != 2) {
            System.out.println("Usage: SumAbcFile <inputFileName> <outputFileName>");
        }
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "UTF-8"));
            String AllLines = "";
            int sum = 0;
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    AllLines += line + "\n";
                }
                for (String CurrentLine : AllLines.toLowerCase().split("[^a-j-]")) {
                    if (!CurrentLine.isEmpty()) {
                        char[] CurrentNumber = CurrentLine.toCharArray();
                        for (int i = 0; i < CurrentNumber.length; i++) {
                            if (Character.isLetter(CurrentNumber[i])) {
                                CurrentNumber[i] = Character.forDigit(CurrentNumber[i] - 'a', 10);
                            }
                        }
                        try {
                            sum += Integer.parseInt(new String(CurrentNumber));
                        } catch (NumberFormatException ex) {
                            System.out.println("I'm not a number, please write letter from [a - j]");
                        }
                    }
                }
            } finally {
                reader.close();
                try {
                    PrintWriter out = new PrintWriter(args[1], "UTF-8");
                    out.print(sum);
                    out.close();
                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.out.println("Can't get second file name");  // Wrong quantity of parameters
                } catch (IOException ex) {
                    System.out.println("Incorrect file name: " + args[1]);     // Impossible to find file at args[1]
                }
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Incorrect number of parameters");      // Wrong quantity of parameters
        } catch (FileNotFoundException ex) {
            System.out.println("Incorrect file name: " + args[0]);      // Can't find file name args[0];
        } catch (IOException ex) {
            System.out.println("Hello it's me (Bug)");
        }
    }
}

