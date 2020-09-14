package utils;

import utils.helpers.BlocksDivider;
import utils.helpers.IndexDoublePair;
import utils.helpers.SymbolCounter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class KeyLengthFinder {

    private final String text;
    private final int keyMinLength;
    private final int keyMaxLength;


    public KeyLengthFinder(String text, int keyMinLength, int keyMaxLength) {
        this.text = text;

        if (keyMinLength > keyMaxLength) {
            throw new IllegalArgumentException(
                    "keyMinLength argument must be less than or equal to keyMaxLength argument"
            );
        }

        this.keyMinLength = keyMinLength;
        this.keyMaxLength = keyMaxLength;
    }


    public KeyLengthFinder(String text) {
        this(text, 6, 10);
    }

    /**
     * @return Looks for the most likely key length
     */
    public int findKeyLength() {
        IndexDoublePair pair = IntStream.range(keyMinLength, keyMaxLength + 1)
                .mapToObj(keyLength -> new IndexDoublePair(keyLength, findCoincidenceIndex(keyLength)))
                .max(Comparator.comparing(IndexDoublePair::getValue))
                .orElseThrow();

        System.out.println("\n\nEstimated key length: " + pair.getIndex());
        return pair.getIndex();
    }


    /**
     * @param keyLength - Probable key length to check
     * @return Coincidence index for given shift
     */
    public Double findCoincidenceIndex(int keyLength) {

        StringBuilder[] blocks = BlocksDivider.divideOnBlocks(text, keyLength);

        ArrayList<Double> probabilities = Arrays
                .stream(blocks)
                .map(block -> {
                    Map<Character, Integer> counter = SymbolCounter.countLetterSymbols(block.toString());
                    return calculateProbability(counter, block.length());
                })
                .collect(Collectors.toCollection(ArrayList::new));


        double meanProbability = probabilities
                .stream()
                .reduce(Double::sum)
                .orElse(0.0) / keyLength;

        // User output to check the correctness of program
        System.out.println("\nProbabilities for " + keyLength + " length key");
        System.out.println(probabilities);
        System.out.println("Mean probability: " + meanProbability + "\n\n");

        return meanProbability;
    }


    /**
     * @param charsMap  Map storing the number of characters in the text
     * @param blockSize Length of text block for given key offset
     * @return Probability of coincidence for given text block.
     */
    private double calculateProbability(Map<Character, Integer> charsMap, int blockSize) {

        double probability = 0;

        for (Map.Entry<Character, Integer> entry : charsMap.entrySet()) {
            double symbolCount = entry.getValue().doubleValue();
            probability += symbolCount * (symbolCount - 1) / (blockSize * (blockSize - 1));
        }

        return probability;
    }
}
