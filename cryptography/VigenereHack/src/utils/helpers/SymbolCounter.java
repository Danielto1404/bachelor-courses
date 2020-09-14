package utils.helpers;

import java.util.HashMap;
import java.util.Map;

public class SymbolCounter {

    public static Map<Character, Integer> countLetterSymbols(String text) {

        HashMap<Character, Integer> counter = new HashMap<>();

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                counter.merge(c, 1, Integer::sum);
            }
        }
        return counter;
    }

}
