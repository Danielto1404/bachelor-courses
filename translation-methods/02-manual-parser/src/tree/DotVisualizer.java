package tree;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DotVisualizer {

    private final Node tree;

    private int index = 0;
    private final String TAB = " ".repeat(4);


    private static final String SEMICOLON = ";";

    /**
     * Constructor for Dot language visualizer.
     *
     * @param tree Parsed tree to visualize.
     */
    public DotVisualizer(Node tree) {
        this.tree = tree;
    }

    /**
     * Takes output file path and writes dot language visualization code.
     * Install plugin `dotplugin` if using Idea.
     *
     * @param outputFile Path of output file.
     * @throws IOException In case path doesn't exist or permission denied.
     */
    public void convert2Dot(String outputFile) throws IOException {

        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(outputFile))) {
            writer.write(
                    "graph ParserTree {"
                            .concat(System.lineSeparator())
                            .concat(genName(tree))
                            .concat(convert(tree, index))
                            .concat("}")
            );
        }
    }

    private String convert(Node node, int from) {

        StringBuilder converted = new StringBuilder();

        for (Node child : node.children) {
            converted.append(genName(child))
                    .append(connection(from, index))
                    .append(convert(child, index));
        }

        return converted.toString();
    }

    private String getNodeName(int index) {
        return "NODE_" + index;
    }

    private String connection(int from, int to) {
        return TAB
                .concat(getNodeName(from))
                .concat(" -- ")
                .concat(getNodeName(to))
                .concat(System.lineSeparator());
    }

    private String genName(Node node) {
        return TAB
                .concat(getNodeName(++index))
                .concat(" [label = ")
                .concat(node.name)
                .concat("]")
                .concat(SEMICOLON)
                .concat(System.lineSeparator());
    }
}
