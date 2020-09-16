package utils;

import utils.helpers.BlocksDivider;
import utils.helpers.SymbolCounter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TextAnalyzer {

    private final int alphabetStartChar;
    private final int alphabetSize;

    public TextAnalyzer(int alphabetSize, char startChar) {
        this.alphabetSize = alphabetSize;
        this.alphabetStartChar = startChar;
    }

    public TextAnalyzer() {
        this(26, 'a');
    }


    public char decodeChar(char keyChar, char encoded) {
        return charByOffset(encoded, alphabetSize - (keyChar - alphabetStartChar));
    }


    public ArrayList<String> generateAllPossibleKeys(String text, int keyLength) {

        var offsets = getKeyCharOffsets(text, keyLength);
        ArrayList<String> keys = new ArrayList<>();

        for (int startSymbol = alphabetStartChar; startSymbol < alphabetStartChar + alphabetSize; ++startSymbol) {

            StringBuilder key = new StringBuilder().append((char) startSymbol);

            for (int offset : offsets) {
                key.append(charByOffset((char) startSymbol, offset));
            }

            keys.add(key.toString());
        }

        return keys;
    }


    private ArrayList<Integer> getKeyCharOffsets(String text, int keyLength) {
        Stream<String> blocksStream = BlocksDivider.divideOnBlocks(text, keyLength);

        String[] blocks = blocksStream.toArray(String[]::new);
        int blocksSize = text.length() / keyLength;

        if (blocks.length == 0) {
            return new ArrayList<>();
        }

        ArrayList<Integer> offsets = new ArrayList<>();
        Map<Character, Integer> anchorMap = SymbolCounter.countLetterSymbols(blocks[0]);

        for (int i = 1; i < blocks.length; ++i) {

            Map<Character, Integer> initialMap = SymbolCounter.countLetterSymbols(blocks[i]);
            Map<Character, Integer> shiftedMap = createValuesShiftedMap(anchorMap);

            int offset = 0;
            double maxCoincidenceIndex = calculateMutualIndexOfCoincidence(initialMap, shiftedMap, blocksSize);

            for (int shift = 1; shift < alphabetSize; ++shift) {
                shiftedMap = createValuesShiftedMap(shiftedMap);
                double currentCoincidenceIndex = calculateMutualIndexOfCoincidence(initialMap, shiftedMap, blocksSize);

                if (currentCoincidenceIndex > maxCoincidenceIndex) {
                    offset = shift;
                    maxCoincidenceIndex = currentCoincidenceIndex;
                }
            }

            offsets.add(offset + 1);
        }

        return offsets;
    }


    private double calculateMutualIndexOfCoincidence(Map<Character, Integer> initial,
                                                     Map<Character, Integer> shifted,
                                                     int blockSize) {

        double coincidenceIndex = 0;

        for (Map.Entry<Character, Integer> keyValueCountEntry : initial.entrySet()) {

            Integer shiftedCountValue = shifted.get(keyValueCountEntry.getKey());
            Integer initialCountValue = keyValueCountEntry.getValue();

            if (shiftedCountValue != null) {
                coincidenceIndex += initialCountValue * shiftedCountValue / (double) (blockSize * blockSize);
            }
        }

        return coincidenceIndex;
    }


    private Map<Character, Integer> createValuesShiftedMap(Map<Character, Integer> initial) {
        Map<Character, Integer> shifted = new HashMap<>();

        initial.forEach((key, value) -> shifted.put(next(key), value));

        return shifted;
    }


    private char charByOffset(char c, int offset) {
        return (char) ((c - alphabetStartChar + offset) % alphabetSize + alphabetStartChar);
    }


    private char next(char c) {
        return charByOffset(c, 1);
    }
}
