import java.io.*;
import java.util.*;

public class WordStatWords {
    public static void main(String[] args) {
        BufferedReader reader;
        int initial = 1;
        Map<String, Integer> wordsCounter = new TreeMap<>();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "UTF-8"));
            String allElements = "";
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    allElements += line + " ";
                }
                for (String CurrentLine : allElements.toLowerCase().split("[^\\p{L}\\p{Pd}\\']+")) {
                    if (!CurrentLine.isEmpty()) {
                        if (wordsCounter.containsKey(CurrentLine)) {
                            wordsCounter.put(CurrentLine, wordsCounter.get(CurrentLine) + 1);
                        } else {
                            wordsCounter.put(CurrentLine, initial);
                        }
                    }
                }
            } catch (IOException | InputMismatchException | IllegalStateException ex) {
                System.out.println("Something goes wrong");
            } finally {
                reader.close();
                try {
                    PrintWriter writer = new PrintWriter(args[1], "UTF-8");
                    for (String keys : wordsCounter.keySet()) {
                        writer.println(keys + " " + wordsCounter.get(keys));
                    }
                    writer.close();
                } catch (IOException | ArrayIndexOutOfBoundsException ex) {
                }
            }
        } catch (
                FileNotFoundException ex) {
            System.out.println("Can't fina file name: " + args[0]);
        } catch (
                UnsupportedEncodingException ex) {
            System.out.println("Unsupported encoding of file");
        } catch (
                ArrayIndexOutOfBoundsException ex) {
            System.out.println("Can't find file name at args[0] position");
        } catch (IOException ex) {
        }
    }
}