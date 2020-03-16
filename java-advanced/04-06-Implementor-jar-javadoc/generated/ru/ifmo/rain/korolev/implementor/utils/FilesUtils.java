package ru.ifmo.rain.korolev.implementor.utils;

import info.kgeorgiy.java.advanced.implementor.ImplerException;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
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
public class FilesUtils {

    /**
     * Deletes all contents of directory {@link File} recursively.
     *
     * @param file Target directory {@link Path}
     */
    public static void deleteDirectories(File file) {
        File[] files = file.listFiles();
        if (files != null) {
            for (File subFile : files) {
                deleteDirectories(subFile);
            }
        }
        file.deleteOnExit();
    }


    /**
     * Compiles .java code to .class file
     *
     * @param token     Class token
     * @param sourceDir path to java code
     * @throws ImplerException if compilation failed
     */
    public static void compile(Class<?> token, Path sourceDir) throws ImplerException {

        Path toSuper = getSuperPath(token);

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new ImplerException("Compiler is not installed");
        }

        String[] compilationArgs = {
                "-cp",
                sourceDir.toString() + File.pathSeparator + toSuper.toString(),
                Path.of(sourceDir.toString(), getPathToImplementation(token) + "Impl.java").toString()
        };

        int compilationResultCode = compiler.run(null, null, null, compilationArgs);
        if (compilationResultCode != 0) {
            throw new ImplerException("Compilation failed (result code is not 0): " + compilationResultCode);
        }

    }

    /**
     * Finds classPath for token
     *
     * @param token Class token
     * @return classPath for given token
     * @throws ImplerException if generation failed
     */
    static Path getSuperPath(Class<?> token) throws ImplerException {
        Path toSuper;
        try {
            CodeSource codeSource = token.getProtectionDomain().getCodeSource();
            if (codeSource == null) {
                toSuper = Path.of("");
            } else {
                toSuper = Path.of(codeSource.getLocation().getPath());
            }
        } catch (InvalidPathException e) {
            throw new ImplerException("Classpath generation failed", e);
        }
        return toSuper;
    }

    /**
     * Creates JAR file
     *
     * @param token     Class token
     * @param sourceDir directory with .class code
     * @param aimPath   directory to save JAR file
     * @throws ImplerException if JAR creation failed
     */
    public static void createJar(Class<?> token, Path sourceDir, Path aimPath) throws ImplerException {
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
    public static Path createTmpDirectory(Path parentDir) throws ImplerException {
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
    public static Path createParentDirectory(Path path) throws ImplerException {
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
        return String.join(File.separator, token.getPackageName().split("\\.")) +
                File.separator + token.getSimpleName();
    }

    /**
     * Creates {@link Path} of implementation source code and create missing parent directories.
     *
     * @param token {@link Class} which implementation is required
     * @param root  Root {@link Path} for implementation files
     * @return {@link Path} where implementation must be created
     * @throws ImplerException In case generated path is invalid
     */
    public static Path makePath(Class<?> token, Path root) throws ImplerException {

        String strPath = getPathToImplementation(token) + "Impl.java";

        Path path;
        try {
            path = Paths.get(root.toString(), strPath);
        } catch (InvalidPathException e) {
            throw new ImplerException("Can't get to file: " + e.getMessage());
        }

        if (path.getParent() != null) {
            try {
                Files.createDirectories(path.getParent());
            } catch (IOException e) {
                throw new ImplerException("Can't create ");
            }
        }
        return path;
    }
}
