package utils;

import java.util.function.BiFunction;

interface Decodable {
    String decode(String encoded);
}

public class Decoder implements Decodable {

    private BiFunction<Character, Integer, Character> decodeRule;

    public Decoder(BiFunction<Character, Integer, Character> decodeRule) {
        this.decodeRule = decodeRule;
    }

    @Override
    public String decode(String encoded) {

        StringBuilder decoded = new StringBuilder();

        for (int i = 0; i < encoded.length(); ++i) {
            Character encodedChar = encoded.charAt(i);
            Character decodedChar = decodeRule.apply(encodedChar, i);
            decoded.append(decodedChar);
        }

        return decoded.toString();
    }
}

