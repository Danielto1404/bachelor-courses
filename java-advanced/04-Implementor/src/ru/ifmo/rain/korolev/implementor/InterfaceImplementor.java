package ru.ifmo.rain.korolev.implementor;

import info.kgeorgiy.java.advanced.implementor.Impler;
import info.kgeorgiy.java.advanced.implementor.ImplerException;
import ru.ifmo.rain.korolev.implementor.utils.ArgumentChecker;
import ru.ifmo.rain.korolev.implementor.utils.ImplementorSourceCodeUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InterfaceImplementor implements Impler {
    private static final String SOURCE_CODE_SUFFIX = "Impl.java";

    @Override
    public void implement(Class<?> token, Path root) throws ImplerException {
        ArgumentChecker.validateToken(token);
        Path sourceCodePath = prepareSourceCodePath(token, root);
        try (BufferedWriter sourceCodeWriter = Files.newBufferedWriter(sourceCodePath)) {
            sourceCodeWriter.write(ImplementorSourceCodeUtils.generateSourceCode(token));
        } catch (IOException e) {
            throw new ImplerException("I/O error occurred", e);
        }
    }

    private static Path prepareSourceCodePath(Class<?> token, Path root) throws ImplerException {
        String sourceCodePath = String.join(File.separator,
                token.getPackageName().split("\\.")) +
                File.separator +
                token.getSimpleName() + SOURCE_CODE_SUFFIX;

        Path path;
        try {
            path = Paths.get(root.toString(), sourceCodePath);
        } catch (InvalidPathException e) {
            throw new ImplerException("Invalid path generated", e);
        }

        Path parentPath = path.getParent();
        if (parentPath != null) {
            try {
                Files.createDirectories(parentPath);
            } catch (IOException e) {
                throw new ImplerException("Failed to prepare source code directory", e);
            }
        }

        return path;
    }


    public static void main(String[] args) throws ImplerException {
        if (args == null || args.length != 2) {
            System.err.println("Usage: InterfaceImplementor <className> <path>");
            return;
        }
        Class<?> token = ArgumentChecker.getTokenByName(args[0]);
        Path root = ArgumentChecker.getPathByName(args[1]);
        try {
            new InterfaceImplementor().implement(token, root);
        } catch (ImplerException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
