package utils.helpers;

import java.util.stream.IntStream;

public class BlocksDivider {

    public static StringBuilder[] divideOnBlocks(String text, int blocksCount) {

        int textLength = text.length();

        int blockSize = textLength / blocksCount;


        StringBuilder[] blocks = IntStream
                .range(0, blocksCount)
                .mapToObj(i -> new StringBuilder())
                .toArray(StringBuilder[]::new);


        for (int i = 0; i < blockSize * blocksCount; ++i) {
            blocks[i % blocksCount].append(text.charAt(i));
        }

        return blocks;
    }

}
