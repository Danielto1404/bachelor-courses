import utils.Decoder;
import utils.KeyLengthFinder;
import utils.TextAnalyzer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class VigenereHacker {

    public static void main(String[] args) {

        String directoryAbsolutePath = System.getProperty("user.dir");
        String pathToPackage = directoryAbsolutePath + "/all/";

        String textIndex = "018";
        String fileName = textIndex + ".cipher";

        String filePath = pathToPackage + fileName;

        try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath), StandardCharsets.UTF_8)) {

            String text = reader.readLine();
            KeyLengthFinder finder = new KeyLengthFinder(text);
            TextAnalyzer analyzer = new TextAnalyzer();

            int keyLength = finder.findKeyLength();
            System.out.println("Estimated key length = " + keyLength);

            analyzer.generateAllPossibleKeys(text, keyLength).forEach(key -> {

                String decoded = new Decoder((c, position) -> {
                    int keyPosition = position % keyLength;
                    return analyzer.decodeChar(key.charAt(keyPosition), c);
                }).decode(text);

                System.out.println("~~~  Key = " + key + "  ~~~");
                System.out.println(decoded);

            });

            // Просмотрев глазами все текста, видно что единственный осмысленный текст получается при ключе iwaivq

        } catch (IOException e) {
            System.out.println("Error occurred while reading file");
        }

    }
}
