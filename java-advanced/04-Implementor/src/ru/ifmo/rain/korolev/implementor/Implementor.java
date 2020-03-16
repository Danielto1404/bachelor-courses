package ru.ifmo.rain.korolev.implementor;

import info.kgeorgiy.java.advanced.implementor.Impler;
import info.kgeorgiy.java.advanced.implementor.ImplerException;
import info.kgeorgiy.java.advanced.implementor.JarImpler;
import ru.ifmo.rain.korolev.implementor.utils.SourceCodeImplementor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ru.ifmo.rain.korolev.implementor.utils.FilesUtils.*;


/**
 * Class implementing {@link Impler}, {@link JarImpler}. Provides public methods to generate abstract class or
 * interface basic implementation and packing it into jar.
 *
 * @author Daniil Korolev
 * @version 239.0
 */
public class Implementor implements Impler, JarImpler {

    /**
     * Produces code implementing class or interface specified by provided token.
     * 
     * Generated class classes name should be same as classes name of the type token with Impl suffix
     * added. Generated source code should be placed in the correct subdirectory of the specified
     * root directory and have correct file name. For example, the implementation of the
     * interface {@link java.util.List} should go to $root/java/util/ListImpl.java
     *
     * @param token type token to create implementation for.
     * @param root  root directory.
     * @throws info.kgeorgiy.java.advanced.implementor.ImplerException when implementation cannot be
     *                                                                 generated.
     */
    @Override
    public void implement(Class<?> token, Path root) throws ImplerException {

        if (token == null || root == null) {
            throw new ImplerException("Arguments must not be null");
        }

        Path pathToSource = makePath(token, root);

        if (token.isPrimitive() || token.isArray() || token.isEnum()) {
            throw new ImplerException("Unsupported type");
        }

        try (BufferedWriter writer = Files.newBufferedWriter(pathToSource)) {
            writer.write(SourceCodeImplementor.getImplementation(token));
        } catch (IOException e) {
            throw new ImplerException("Can't open file: " + e.getMessage());
        }
    }


    /**
     * Produces .jar file implementing class or interface specified by provided token.
     * 
     * Generated class classes name should be same as classes name of the type token with Impl suffix
     * added.
     *
     * @param token   type token to create implementation for.
     * @param jarFile target .jar file.
     * @throws ImplerException when implementation cannot be generated.
     */
    @Override
    public void implementJar(Class<?> token, Path jarFile) throws ImplerException {
        if (token == null || jarFile == null) {
            throw new ImplerException("Arguments must not be null");
        }

        Path parentDir = createParentDirectory(jarFile);
        Path sourceDir = createTmpDirectory(parentDir);

        try {
            implement(token, sourceDir);
            compile(token, sourceDir);
            createJar(token, sourceDir, jarFile);
        } catch (ImplerException e) {
            throw new ImplerException("Implementation failed", e);
        } finally {
            deleteDirectories(sourceDir.toFile());
        }

    }

    /**
     * Main method
     * 
     * Usage: ([-jar]) [ClassName] [Path]
     *
     * @param args Provided to program arguments
     */
    public static void main(String[] args) {

        if (args == null || !(args.length == 2 || args.length == 3)) {
            System.out.println("Wrong arguments format, usage:  [-jar]  className path");
            return;
        }
        for (String arg : args) {
            if (arg == null) {
                System.out.println("Arguments must not be null");
                return;
            }
        }

        boolean jarMode = args.length == 3;
        int startIndex = jarMode ? 1 : 0;
        if (jarMode && !(args[0].equals("-jar"))) {
            System.out.println("Wrong arguments format, usage:  [-jar]  className path");
            return;
        }

        Class<?> token;
        try {
            token = Class.forName(args[startIndex]);
        } catch (
                ClassNotFoundException e) {
            System.out.println("Class not found!");
            return;
        }

        Path root;
        try {
            root = Paths.get(args[startIndex + 1]);
        } catch (
                InvalidPathException e) {
            System.out.println("Can't get parent directory");
            return;
        }

        Implementor implementor = new Implementor();

        try {
            if (jarMode) {
                implementor.implementJar(token, root);
            } else {
                implementor.implement(token, root);
            }
        } catch (ImplerException e) {
            System.out.println(e.getMessage());
        }
    }
}
