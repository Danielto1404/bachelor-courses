package ru.ifmo.rain.korolev.implementor.utils;

import info.kgeorgiy.java.advanced.implementor.ImplerException;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ArgumentChecker {

    public static void nullAssertion(String... args) throws ImplerException {
        for (String s : args) {
            if (s == null) {
                throw new ImplerException("Null arguments appeared");
            }
        }
    }

    /**
     * Validates whether {@link Class} attributes and modifiers allow common implementation.
     *
     * @param token {@link Class} which implementation is required
     * @throws ImplerException In case check fails
     */
    public static void checkValidity(Class<?> token) throws ImplerException {
        if (token.isPrimitive() || token.isArray() || token == Enum.class) {
            throw new ImplerException("Unsupported token given");
        }
    }


    public static Class<?> getTokenByName(String className) throws ImplerException {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new ImplerException("Error: Class not found by name");
        }
    }

    public static Path getPathByName(String pathName) throws ImplerException {
        try {
            return Paths.get(pathName);
        } catch (InvalidPathException e) {
            throw new ImplerException("Error: Invalid root directory");
        }
    }
}