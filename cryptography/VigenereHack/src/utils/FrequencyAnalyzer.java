package utils;

import utils.helpers.CharFrequencyPair;
import utils.helpers.IndexDoublePair;
import utils.helpers.SymbolCounter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FrequencyAnalyzer {

    private final ArrayList<CharFrequencyPair> frequencies;
    private static final int startCharCode = 'a';

    public FrequencyAnalyzer() {

        frequencies = new ArrayList<>();

        frequencies.add(new CharFrequencyPair('a', 0.08167));
        frequencies.add(new CharFrequencyPair('b', 0.01492));
        frequencies.add(new CharFrequencyPair('c', 0.02782));
        frequencies.add(new CharFrequencyPair('d', 0.04253));
        frequencies.add(new CharFrequencyPair('e', 0.12702));
        frequencies.add(new CharFrequencyPair('f', 0.02228));
        frequencies.add(new CharFrequencyPair('g', 0.02015));
        frequencies.add(new CharFrequencyPair('h', 0.06094));
        frequencies.add(new CharFrequencyPair('i', 0.06966));
        frequencies.add(new CharFrequencyPair('j', 0.00153));
        frequencies.add(new CharFrequencyPair('k', 0.00772));
        frequencies.add(new CharFrequencyPair('l', 0.04025));
        frequencies.add(new CharFrequencyPair('m', 0.02406));
        frequencies.add(new CharFrequencyPair('n', 0.06749));
        frequencies.add(new CharFrequencyPair('o', 0.07507));
        frequencies.add(new CharFrequencyPair('p', 0.01929));
        frequencies.add(new CharFrequencyPair('q', 0.00095));
        frequencies.add(new CharFrequencyPair('r', 0.05987));
        frequencies.add(new CharFrequencyPair('s', 0.06327));
        frequencies.add(new CharFrequencyPair('t', 0.09056));
        frequencies.add(new CharFrequencyPair('u', 0.02758));
        frequencies.add(new CharFrequencyPair('v', 0.00978));
        frequencies.add(new CharFrequencyPair('w', 0.0236));
        frequencies.add(new CharFrequencyPair('x', 0.0015));
        frequencies.add(new CharFrequencyPair('y', 0.01975));
        frequencies.add(new CharFrequencyPair('z', 0.00074));
    }


    public Map<Character, ArrayList<Character>> getClosestFrequencyCoincidences(String text,
                                                                                int keyLength,
                                                                                int keyPosition) {

        StringBuilder extractedKeyPositionChars = new StringBuilder();

        for (int i = keyPosition; i < text.length(); i += keyLength) {
            extractedKeyPositionChars.append(text.charAt(i));
        }


        int extractedLength = extractedKeyPositionChars.length();
        Map<Character, Integer> counted = SymbolCounter.countLetterSymbols(extractedKeyPositionChars.toString());

        Map<Character, ArrayList<Character>> probableDecodedChars = new HashMap<>();

        for (Map.Entry<Character, Integer> entry : counted.entrySet()) {

            ArrayList<Character> nClosestCharsArray =
                    findNClosest(
                            (double) entry.getValue() / extractedLength);

            probableDecodedChars.put(entry.getKey(), nClosestCharsArray);
        }


        return probableDecodedChars;
    }


    public char charByOffset(char c, int offset) {
        return (char) ((c - startCharCode + offset) % frequencies.size() + startCharCode);
    }


    public char next(char after) {
        return charByOffset(after, 1);
    }


    public IndexDoublePair getStartOffsetAndMeanValue(Map<Character, ArrayList<Character>> chars) {

        int minMeanValue = Integer.MAX_VALUE;
        int startOffset = -1;

        for (int startIndex = 0; startIndex < frequencies.size(); ++startIndex) {

            int meanDistance = startIndex;

            char key = frequencies.get(0).getSymbol();
            char start = chars.get(key).get(startIndex);

            for (int i = 1; i < frequencies.size(); ++i) {
                key = next(key);
                start = next(start);

                ArrayList<Character> coincidenceChars = chars.get(key);

                meanDistance += coincidenceChars == null ? frequencies.size() : coincidenceChars.indexOf(start);

            }

            if (meanDistance < minMeanValue) {
                minMeanValue = meanDistance;
                startOffset = startIndex;
            }
        }

        return new IndexDoublePair(startOffset, (double) minMeanValue);
    }


    public char decodeChar(char keyChar, char encoded) {
        return charByOffset(encoded, frequencies.size() - (keyChar - startCharCode));
    }


    public char decodedKeyChar(int keyPosition, Character convertedFromA, String initialText) {
        char textChar = initialText.charAt(keyPosition);
        return decodeChar(convertedFromA, textChar);
    }


    private ArrayList<Character> findNClosest(double probability) {
        return frequencies
                .stream()
                .sorted(Comparator.comparingDouble(x -> Math.abs(x.getFrequency() - probability)))
                .map(CharFrequencyPair::getSymbol)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
