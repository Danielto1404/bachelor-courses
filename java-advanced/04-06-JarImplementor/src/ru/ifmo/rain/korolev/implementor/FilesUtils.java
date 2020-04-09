package ru.ifmo.rain.korolev.implementor;

import info.kgeorgiy.java.advanced.implementor.ImplerException;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.jar.Attributes;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

/**
 * Class with tools for Implementor
 *
 * @author Daniil Korolev
 * @version 239.0
 */
class FilesUtils {

    /**
     * Deletes all contents of directory {@link File} recursively.
     *
     * @param file Target directory {@link Path}
     */
    static void deleteDirectories(File file) {
        File[] files = file.listFiles();
        if (files != null) {
            for (File subFile : files) {
                deleteDirectories(subFile);
            }
        }
        file.deleteOnExit();
    }

    /**
     * @param token {@link Class} which classpath is required
     * @return {@link String} representation of token classpath
     */
    private static Path getClassPath(Class<?> token) {
        try {
            return Path.of(token.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (final URISyntaxException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Compiles the solution.
     *
     * @param token     the {@link Class} token of implemented class
     * @param sourceDir the temp directory with classes
     * @throws ImplerException if an error occurs while compiling the result or searching for classpath
     */
    static void compile(Class<?> token, Path sourceDir) throws ImplerException {

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new ImplerException("Compiler is not provided");
        }

        String[] compilationArgs = {"-encoding", "UTF-8",
                "-cp",
                sourceDir.toString() + File.pathSeparator + getClassPath(token),
                Path.of(sourceDir.toString(), getPathToImplementation(token) + "Impl.java").toString()
        };

        int compilationResultCode = compiler.run(null, null, null, compilationArgs);
        if (compilationResultCode != 0) {
            throw new ImplerException("Compilation failed (result code: " + compilationResultCode + ")");
        }

    }

    /**
     * Creates the jar file for the solution.
     *
     * @param token     the {@link Class} token of implemented class
     * @param sourceDir the temp directory with classes
     * @param aimPath   name of jar file to be generated
     * @throws ImplerException if an error occurs while creating jar file
     */
    static void createJar(Class<?> token, Path sourceDir, Path aimPath) throws ImplerException {
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");

        try (JarOutputStream stream = new JarOutputStream(Files.newOutputStream(aimPath), manifest)) {
            String implementationPath = getPathToImplementation(token) + "Impl.class";
            stream.putNextEntry(new ZipEntry(implementationPath));
            Files.copy(Path.of(sourceDir.toString(), implementationPath), stream);
        } catch (IOException e) {
            throw new ImplerException("Can't create Jar", e);
        }
    }

    /**
     * Creates temporary directory in given {@link Path}.
     *
     * @param parentDir {@link Path} where temporary directory needs to be created
     * @return {@link Path} of created temporary directory
     * @throws ImplerException In case I/O exception occurred in process
     */
    static Path createTempFolder(Path parentDir) throws ImplerException {
        Path sourceDir;
        try {
            sourceDir = Files.createTempDirectory(parentDir, "impler-tmp-dir");
        } catch (IOException e) {
            throw new ImplerException("Can't create temp directories", e);
        }
        return sourceDir;
    }

    /**
     * Creates missing parent directories of given path.
     *
     * @param path {@link Path} to generate parent directories for
     * @return Generated parent directory {@link Path}
     * @throws ImplerException In case I/O exception occurred in process
     */
    static Path createParentFolders(Path path) throws ImplerException {
        Path parentPath = path.toAbsolutePath().normalize().getParent();
        if (parentPath == null) {
            return null;
        } else {
            try {
                Files.createDirectories(parentPath);
            } catch (IOException e) {
                throw new ImplerException("Can't create folders", e);
            }
            return parentPath;
        }
    }


    /**
     * Provides valid source code directories. Formed considering package
     * and name.
     *
     * @param token {@link Class} which implementation is required
     * @return Path to source code as {@link String}
     */
    static String getPathToImplementation(Class<?> token) {
        return String.join("/", token.getPackageName().split("\\.")) +
                "/" + token.getSimpleName();
    }


    /**
     * Creates {@link Path} of implementation source code and create missing parent directories.
     *
     * @param token   {@link Class} which implementation is required
     * @param rootDir Root {@link Path} for implementation files
     * @return {@link Path} where implementation must be created
     * @throws ImplerException In case generated path is invalid
     */
    static Path makePath(Class<?> token, Path rootDir) throws ImplerException {

        String tokenPath = getPathToImplementation(token) + "Impl.java";
        Path fullPath;
        try {
            fullPath = Paths.get(rootDir.toString(), tokenPath);
        } catch (InvalidPathException e) {
            throw new ImplerException("Can't get to file: " + e.getMessage());
        }

        if (fullPath.getParent() != null) {
            try {
                Files.createDirectories(fullPath.getParent());
            } catch (IOException e) {
                throw new ImplerException("Can't create ");
            }
        }
        return fullPath;
    }
}
