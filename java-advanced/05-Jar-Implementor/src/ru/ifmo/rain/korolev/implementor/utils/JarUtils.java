package ru.ifmo.rain.korolev.implementor.utils;

import info.kgeorgiy.java.advanced.implementor.ImplerException;
import ru.ifmo.rain.korolev.implementor.Implementor;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.security.CodeSource;
import java.util.jar.Attributes;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

import static ru.ifmo.rain.korolev.implementor.utils.FileUtils.getImplementationPath;

/**
 * Assisting class to {@link Implementor}. Provides tools for necessary operations
 * with <code>JAR</code>.
 *
 * @author Ian Dolzhanskii (yan.dolganskiy@mail.ru)
 * @version 0.9
 */
public class JarUtils {
    /**
     * Compiles code of token implementation stored in temporary directory.
     * Searches for abstract super class or interface and adds it to classpath.
     * Requires compiler to be available in the system.
     *
     * @param token  {@link Class} to compile implementation of
     * @param tmpDir {@link Path} of directory where implementation source code is
     *               stored
     * @throws ImplerException In case generated path to source code is invalid
     * @throws ImplerException In case no compiler is provided
     * @throws ImplerException In case compilation finished with non-zero return code
     */
    public static void compileCode(Class<?> token, Path tmpDir) throws ImplerException {
        Path superPath;
        try {
            CodeSource superCodeSource = token.getProtectionDomain().getCodeSource();
            superPath = Path.of((superCodeSource == null) ? "" : superCodeSource.getLocation().getPath());
        } catch (InvalidPathException e) {
            throw new ImplerException("Failed to generate valid classpath", e);
        }

        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        if (javaCompiler == null) {
            throw new ImplerException("No compiler provided");
        }

        String[] compilerArgs = {
                "-cp",
                tmpDir.toString() + File.pathSeparator + superPath.toString(),
                Path.of(tmpDir.toString(), getImplementationPath(token) + "Impl.java").toString(),
        };

        int returnCode = javaCompiler.run(null, null, null, compilerArgs);
        if (returnCode != 0) {
            throw new ImplerException("Implementation compilation returned non-zero code " + returnCode);
        }
    }

    /**
     * Creates <code>JAR</code> containing compiled implementation of <code>token</code>
     * at given {@link Path}.
     *
     * @param token      {@link Class} to pack implementation of
     * @param tmpDir     {@link Path} where source code is stored
     * @param targetPath {@link Path} where resulting <code>JAR</code> must be created
     * @throws ImplerException In case I/O error occurred
     */
    public static void createJar(Class<?> token, Path tmpDir, Path targetPath) throws ImplerException {
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");

        try (JarOutputStream stream = new JarOutputStream(Files.newOutputStream(targetPath), manifest)) {
            String implementationPath = getImplementationPath(token) + "Impl.class";
            stream.putNextEntry(new ZipEntry(implementationPath));
            Files.copy(Path.of(tmpDir.toString(), implementationPath), stream);
        } catch (IOException e) {
            throw new ImplerException("Failed to write JAR", e);
        }
    }
}
