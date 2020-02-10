import java.io.*;
import java.util.*;

public class WordStatCount {
    public static void main(String[] args) {
        BufferedReader reader;
        Map<String, Integer> Words = new LinkedHashMap<>();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "UTF-8"));
            String AllElements = "";
            try {
                String Line;
                while ((Line = reader.readLine()) != null) {
                    AllElements += Line + "\n";
                }
                for (String CurrentLine : AllElements.toLowerCase().split("[^\\p{Pd}\\p{L}\\']")) {
                    if (!CurrentLine.isEmpty()) {
                        if (Words.containsKey(CurrentLine)) {
                            Words.put(CurrentLine, Words.get(CurrentLine) + 1);
                        } else {
                            Words.put(CurrentLine, 1);
                        }
                    } else {
                        System.out.println("I don't have words in this line, please write another line of words");
                    }
                }
            } catch (IOException ex) {
                System.out.println("It may not read next line, don't know why, please try again");
            } finally {
                reader.close();
                try {
                    PrintWriter Out = new PrintWriter(args[1], "UTF-8");
                    Words.entrySet().stream().sorted(Map.Entry.comparingByValue()).
                            forEach(pair -> Out.println(pair.getKey() + " " + pair.getValue()));
                    Out.close();
                } catch (IOException ex) {

                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException at args [0]");
        } catch (UnsupportedEncodingException ex) {
            System.out.println("I' am sure in my Encoding name");
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Can't find file name at args[0]");
        } catch (IOException ex) {
            System.out.println("Some file Exception");
        }
    }
}

