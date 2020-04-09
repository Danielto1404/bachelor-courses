package ru.ifmo.rain.korolev.implementor;

import info.kgeorgiy.java.advanced.implementor.Impler;
import info.kgeorgiy.java.advanced.implementor.ImplerException;
import info.kgeorgiy.java.advanced.implementor.JarImpler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ru.ifmo.rain.korolev.implementor.FilesUtils.*;

/**
 * Class implementing {@link Impler}, {@link JarImpler}. Provides methods to generate classes and interfaces.
 *
 * @author Korolev Daniil
 * @version 239.0
 */
public class JarImplementor implements Impler, JarImpler {

    private static final String USAGE = "([-jar])  [ClassName] [Path]";

    /**
     * Default constructor
     */
    public JarImplementor() {
    }

    /**
     * Implements the given class.
     * Invokes {@link NativeCodeImplementor#getImplementation(Class)} to generate the code of the implementing class
     *
     * @param token the {@link Class} token of implemented class
     * @param root  the directory for locating the implementation
     * @throws ImplerException if an error occurs while writing the result into the output file
     * @see NativeCodeImplementor#getImplementation(Class)
     */
    @Override
    public void implement(Class<?> token, Path root) throws ImplerException {

        if (token == null || root == null) {
            throw new ImplerException("Arguments must not be null");
        }

        Path pathToSource = makePath(token, root);

        int modifiers = token.getModifiers();
        if (token.isPrimitive() || token.isArray() || token.isEnum() || Modifier.isFinal(modifiers) || Modifier.isPrivate(modifiers)) {
            throw new ImplerException("Unsupported type");
        }

        try (BufferedWriter writer = Files.newBufferedWriter(pathToSource)) {
            writer.write(NativeCodeImplementor.getImplementation(token));
        } catch (IOException e) {
            throw new ImplerException("Can't open file: " + e.getMessage());
        }
    }


    /**
     * Invoked in case the program was run with -jar key.
     * Creates the temporary directory and invokes methods for implementing, compiling, creating jar file.
     *
     * @param token   the {@link Class} token of implemented class
     * @param jarFile name of jar file to be generated
     * @throws ImplerException if an error occurs while creating parent path for jar or temporary directory for source
     * @see FilesUtils#createJar(Class, Path, Path)
     * @see FilesUtils#compile(Class, Path)
     * @see JarImplementor#implement(Class, Path)
     */
    @Override
    public void implementJar(Class<?> token, Path jarFile) throws ImplerException {
        if (token == null || jarFile == null) {
            throw new ImplerException("Arguments must not be null");
        }

        Path parentDir = createParentFolders(jarFile);
        Path sourceDir = createTempFolder(parentDir);

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
     * Main method that provides console interface.
     * Usage: ([-jar]) [ClassName] [Path]
     *
     * @param args arguments entered in command line
     * @see JarImplementor#implement(Class, Path)
     * @see JarImplementor#implementJar(Class, Path)
     */
    public static void main(String[] args) {

        if (args == null || !(args.length == 2 || args.length == 3)) {
            System.out.println("Wrong arguments format, usage: " + USAGE);
            return;
        }
        for (String arg : args) {
            if (arg == null) {
                System.out.println("Arguments must not be null");
                return;
            }
        }

        boolean jarMode;
        if (args.length == 2) {
            jarMode = false;
        } else {
            if (args[0].equals("-jar")) {
                jarMode = true;
                args[0] = args[1];
                args[1] = args[2];
            } else {
                System.out.println("Wrong arguments format, usage: " + USAGE);
                return;
            }
        }

        Class<?> token;
        try {
            token = Class.forName(args[0]);
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found!");
            return;
        }

        Path root;

        try {
            root = Paths.get(args[1]);
        } catch (InvalidPathException e) {
            System.out.println("Can't get parent directory");
            return;
        }

        JarImplementor implementor = new JarImplementor();

        try {
            if (!jarMode) {
                implementor.implement(token, root);
            } else {
                implementor.implementJar(token, root);
            }
        } catch (ImplerException e) {
            System.out.println(e.getMessage());
        }
    }
}
