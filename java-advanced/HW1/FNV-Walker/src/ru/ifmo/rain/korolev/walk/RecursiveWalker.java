package ru.ifmo.rain.korolev.walk;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.stream.Stream;


public class RecursiveWalker {

    public static void main(String[] args) {
        if (!checkInputArguments(args)) {
            return;
        }
        Path inputPath = createPath(args[0]);
        Path outputPath = createPath(args[1]);
        if (inputPath == null || outputPath == null) {
            return;
        }
        try {
            if (outputPath.getParent() != null) {
                Files.createDirectories(outputPath.getParent());
            }
        } catch (IOException e) {
            System.err.println("Can't make an output file");
            return;
        }
        run(inputPath, outputPath);
    }

    private static Path createPath(String stringPath) {
        try {
            return Paths.get(stringPath);
        } catch (InvalidPathException e) {
            System.err.println("Invalid path: " + stringPath);
            return null;
        }
    }

    private static boolean checkInputArguments(String[] args) {
        if (args == null || args.length != 2 || args[0] == null || args[1] == null) {
            System.err.println("Usage: java RecursiveWalk <input file name> <output file name>");
            return false;
        }
        return true;
    }

    private static void run(Path inputPath, Path outputPath) {
        try (BufferedWriter bw = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)) {
            try (Stream<String> inputStream = Files.lines(inputPath, StandardCharsets.UTF_8)) {
                inputStream.forEach(fileName ->
                {
                    Path filePath = createPath(fileName);
                    if (filePath == null) {
                        writeError(bw, fileName);
                        return;
                    }
                    try (Stream<Path> pathStream = Files.walk(filePath)) {
                        pathStream.filter(Predicate.not(Files::isDirectory)).forEach(
                                path -> write(bw, FNVHasher.calculateHash(path), path.toString()));
                    } catch (IOException e) {
                        System.err.println("Can't read file: " + fileName);
                        writeError(bw, fileName);
                    }
                });

            } catch (FileNotFoundException e) {
                System.err.println("File not found: " + inputPath.toString());
            } catch (UncheckedIOException e) {
                System.err.println("Invalid Input File");
            }

        } catch (IOException e) {
            System.err.println("Trying to Open an Output File Error Occurred");
        }
    }

    private static void write(Writer writer, int hash, String pathName) {
        try {
            writer.write(String.format("%08x %s%n", hash, pathName));
        } catch (IOException e) {
            System.out.println("Error writing into output file");
        }
    }

    private static void writeError(Writer writer, String pathName) {
        write(writer, 0, pathName);
    }
}