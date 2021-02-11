package conversion;

import antlr.CPPLexer;
import antlr.CPPParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class CPPFormatter {

    private static final String USAGE = "USAGE: <file_to_format> <output_formatted_file>";

    public static String format(Path inputPath) throws IOException {
        CPPParser parser = getParser(inputPath);
        ParseTree cppParsedTree = parser.cpp();
        FormatVisitor visitor = new FormatVisitor();
        visitor.visit(cppParsedTree);
        return visitor.getFormattedCode().trim();
    }

    private static CPPParser getParser(Path inputPath) throws IOException {
        CharStream stream = CharStreams.fromPath(inputPath);
        CPPLexer lexer = new CPPLexer(stream);
        TokenStream ts = new CommonTokenStream(lexer);
        return new CPPParser(ts);
    }

    public static void main(String[] args) throws IOException {
        if (args == null || args.length != 2 || args[0] == null || args[1] == null) {
            System.err.println(USAGE);
            return;
        }

        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(args[1]))) {
            Path inputPath = Path.of(args[0]);
            String formatted = format(inputPath);
            writer.write(formatted);
        }
    }
}