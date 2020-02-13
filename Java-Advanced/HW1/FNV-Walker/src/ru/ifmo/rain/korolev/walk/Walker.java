package ru.ifmo.rain.korolev.walk;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Walker {

    public static void main(String[] args) {
        if (args == null || args.length != 2 || args[0] == null || args[1] == null) {
            System.out.println("Usage java Walk <input file name> <output file name>");
            return;
        }
        Path inputFilePath = Path.of(args[0]);
        Path outputFilePath = Path.of(args[1]);
        try (FileWriter fileWriter = new FileWriter(outputFilePath.toString(), StandardCharsets.UTF_8)) {
            try (Stream<String> inputFiles = Files.lines(inputFilePath, StandardCharsets.UTF_8)) {
                inputFiles.forEach(filePathName -> {
                    int hash = FNVHasher.calculateHash(Path.of(filePathName));
                    write(fileWriter, hash, filePathName);
                });
            } catch (IOException e) {
                System.out.println("Unable to read input file " + inputFilePath.toString());
            }
        } catch (InvalidPathException | IOException e) {
            System.out.println("Can't open output file " + outputFilePath.toString());
        }
    }

    private static void write(Writer writer, int hash, String outputPathName) {
        try {
            writer.write(String.format("%08x %s%n", hash, outputPathName));
        } catch (IOException e) {
            System.out.println("Error writing into output file " + outputPathName);
        }
    }
}
