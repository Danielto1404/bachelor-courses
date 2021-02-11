import lexer.Lexer;
import lexer.LexerException;
import parser.Parser;
import parser.ParserException;
import tree.DotVisualizer;
import tree.Node;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    private static final String format = "ARGUMENTS FORMAT: <input_file> <output_file>";

    /**
     * Takes input_path and out_path as command line arguments.
     * Writes to output_path Dot language markup of parsed tree of
     * Python definition code line from input_path.
     *
     * @param args input_file output_file [image_file_path]"
     * @throws IOException     in case input or output path are in invalid.
     * @throws LexerException  in case unexpected token occurs.
     * @throws ParserException in case unexpected token occurs.
     */
    public static void main(String[] args) throws IOException, LexerException, ParserException {
        if (args.length != 2 && args.length != 3) {
            System.err.println("Invalid number of arguments: " + format);
            return;
        }

        String inputPath = args[0];
        String outputPath = args[1];
        String imageFilePath = null;

        if (args.length == 3) {
            imageFilePath = args[2];
        }

        if (inputPath == null || outputPath == null) {
            System.err.println("Arguments should not be null: " + format);
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(Path.of(inputPath))) {

            Lexer lexer = new Lexer(reader);
            Parser parser = new Parser(lexer);
            Node parsedTree = parser.parse();

            DotVisualizer visualizer = new DotVisualizer(parsedTree);

            visualizer.convert2Dot(outputPath);

            if (imageFilePath != null) {
                makeDotSVG(outputPath, imageFilePath);
            }
        }
    }

    private static void makeDotSVG(String execute, String outputFile) throws IOException {
        new ProcessBuilder("dot", "-Tsvg", execute)
                .redirectOutput(new File(outputFile))
                .start();
    }
}
