package utils.helpers;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BlocksDivider {

    public static Stream<String> divideOnBlocks(String text, int blocksCount) {

        int textLength = text.length();

        int blockSize = textLength / blocksCount;


        StringBuilder[] blocks = IntStream
                .range(0, blocksCount)
                .mapToObj(i -> new StringBuilder())
                .toArray(StringBuilder[]::new);


        for (int i = 0; i < blockSize * blocksCount; ++i) {
            blocks[i % blocksCount].append(text.charAt(i));
        }

        return Arrays.stream(blocks)
                .map(StringBuilder::toString);
    }
}
