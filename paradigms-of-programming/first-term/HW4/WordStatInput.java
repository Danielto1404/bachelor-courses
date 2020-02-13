import java.io.*;
import java.util.ArrayList;

public class WordStatInput {
    public static void main(String[] args) {
        ArrayList<String> Words = new ArrayList<>();
        int[] NumberOfWords = {0};
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "UTF-8"));
            String AllLines = "";
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    AllLines += line + " ";
                }
                NumberOfWords = new int[AllLines.length()];
                for (String CurrentWord : AllLines.toLowerCase().split(("[^\\p{L}\\p{Pd}\\']+"))) {
                    if (!CurrentWord.isEmpty()) {
                        if (Words.contains(CurrentWord) == false) {
                            Words.add(CurrentWord);
                        }
                        int CurrentIndex = Words.indexOf(CurrentWord);
                        NumberOfWords[CurrentIndex]++;
                    }
                }
            } catch (IOException ex) {
                System.out.println("It was determined IOException");
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("Out of bounds");
            } finally {
                reader.close();
                try {
                    PrintWriter writer = new PrintWriter(args[1], "UTF-8");
                    for (int i = 0; i < Words.size(); i++) {
                        writer.println(Words.get(i) + " " + NumberOfWords[i]);
                    }
                    writer.close();
                } catch (IndexOutOfBoundsException ex) {
                    System.out.println("Can't find file name at args[1] position");
                } catch (FileNotFoundException ex) {
                    System.out.println("Can't find file name: " + args[1]);
                } catch (IOException ex) {
                    System.out.println("Some IOException");
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Can't find file name: " + args[0]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Impossible to find file name at args[0] position");
        } catch (UnsupportedEncodingException ex) {
            System.out.println("Unsupported encoding charsetName");
        } catch (IOException ex) {
            System.out.println("Some IOException");
        }
    }
}
