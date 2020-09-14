import utils.Decoder;
import utils.FrequencyAnalyzer;
import utils.KeyLengthFinder;
import utils.helpers.IndexDoublePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;

public class VigenereHacker {

    public static void main(String[] args) {

        String textIndex = "018";
        String fileName = textIndex + ".cipher";

        String filePath = "/Users/daniilkorolev/Documents/GitHub/University/cryptography/VigenereHack/all/" +
                fileName;

        try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath), StandardCharsets.UTF_8)) {

            String text = reader.readLine();
            KeyLengthFinder finder = new KeyLengthFinder(text);
            FrequencyAnalyzer analyzer = new FrequencyAnalyzer();

            int keyLength = finder.findKeyLength();

            StringBuilder key = new StringBuilder();

            for (int keyPosition = 0; keyPosition < keyLength; ++keyPosition) {

                Map<Character, ArrayList<Character>> chars = analyzer.getClosestFrequencyCoincidences(text, keyLength, keyPosition);
                IndexDoublePair startOffsetCoincidenceValue = analyzer.getStartOffsetAndMeanValue(chars);
                char convertedFromA = chars.get('a').get(startOffsetCoincidenceValue.getIndex());

                key.append(analyzer.decodedKeyChar(keyPosition, convertedFromA, text));

                System.out.println("\n\n" + chars);
                System.out.println("Current key char offset for " + keyPosition + " keyPosition: "
                        + startOffsetCoincidenceValue
                        + "\na -> " + convertedFromA);
            }

            System.out.println("\n\n~~~~~~~~~~~~~~~ Key = " + key + " ~~~~~~~~~~~~~~~\n");


            Decoder decoder = new Decoder((encoded, pos) -> {
                int shift = pos % keyLength;
                return analyzer.decodeChar(key.charAt(shift), encoded);
            });

            System.out.println("\n~~~~~~~~~~~~~~~ Decoded text ~~~~~~~~~~~~~~~\n");
            System.out.println(decoder.decode(text));


        } catch (IOException e) {
            System.out.println("Error occurred while reading file");
        }

    }
}
