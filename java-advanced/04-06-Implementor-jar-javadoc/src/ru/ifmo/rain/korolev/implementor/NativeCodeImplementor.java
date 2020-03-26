package ru.ifmo.rain.korolev.implementor;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Assisting class to {@link JarImplementor}. Provides tools for necessary operations
 * for implementation source code generation.
 *
 * @author Korolev Daniil
 * @version 239.0
 */
public class NativeCodeImplementor {

    private static final String EMPTY = "";
    private static final String SPACE = " ";
    private static final String COMMA_WITH_SPACE = "," + SPACE;
    private static final String TAB = "\t";
    private static final String NEWLINE = System.lineSeparator();
    private static final String SEMICOLON = ";";
    private static final String LBRACE = "(";
    private static final String RBRACE = ")";
    private static final String BLOCK_OPENED = "{";
    private static final String BLOCK_CLOSED = "}";

    private static final String PACKAGE = "package";
    private static final String PUBLIC = "public";
    private static final String CLASS = "class";
    private static final String IMPLEMENTS = "implements";
    private static final String THROWS = "throws";
    private static final String RETURN = "return";

    private static final String NULL = "null";
    private static final String FALSE = "false";
    private static final String ZERO = "0";

    private static final String IMPL_SUFFIX = "Impl";


    /**
     * Generates {@link String} declaring implementation class package if available.
     *
     * @param token {@link Class} which implementation is required
     * @return Package declaration {@link String} if token is in package or empty
     * {@link String} otherwise
     */
    private static String getPackage(Class<?> token) {
        String pkg = token.getPackageName();
        return pkg == null ? EMPTY : String.join(SPACE, PACKAGE, pkg, SEMICOLON);
    }

    /**
     * Generates class opening line. Includes modifiers, name and super class.
     *
     * @param token {@link Class} which implementation is required
     * @return Implementation class opening line
     */
    private static String getClassHeader(Class<?> token) {
        return String.join(SPACE,
                PUBLIC, CLASS, token.getSimpleName() + IMPL_SUFFIX, IMPLEMENTS, token.getCanonicalName());
    }

    /**
     * Generates {@link String} declaring or enumerating {@link Method} arguments
     * separated by comma.
     *
     * @param method {@link Method} which arguments are required
     * @return {@link String} of arguments list
     */
    private static String getArguments(Method method) {
        NameGenerator nameGenerator = new NameGenerator();
        return Arrays.stream(method.getParameterTypes())
                .map(arg -> arg.getCanonicalName() + SPACE + nameGenerator.get())
                .collect(Collectors.joining(COMMA_WITH_SPACE));
    }

    /**
     * Generates method definition. Includes modifiers, return code if it is
     * an instance of {@link Method}, name, generated args and possible exceptions.
     *
     * @param method {@link Method} which opening line is required
     * @return Opening {@link String} of requested {@link Method}
     */
    private static String getMethodDefinition(Method method) {
        String returnType = method.getReturnType().getCanonicalName();
        String name = method.getName();
        return String.join(SPACE, PUBLIC, returnType, name)
                .concat(LBRACE + getArguments(method) + RBRACE)
                .concat(SPACE)
                .concat(getMethodExceptions(method))
                .concat(SPACE)
                .concat(BLOCK_OPENED);
    }

    /**
     * Generates source code of default value.
     *
     * @param method {@link Method} to find default value of
     * @return {@link String} source code of default value
     */
    private static String getDefaultValue(Method method) {
        Class<?> token = method.getReturnType();
        if (!token.isPrimitive()) {
            return NULL;
        } else if (token.equals(void.class)) {
            return EMPTY;
        } else if (token.equals(boolean.class)) {
            return FALSE;
        } else {
            return ZERO;
        }
    }

    /**
     * Generates {@link Method} complete code.
     *
     * @param method {@link Method} which body is required
     * @return Implementation {@link String} of required {@link Method}
     */
    private static String getMethodDeclaration(Method method) {
        return TAB.repeat(2)
                .concat(RETURN)
                .concat(SPACE)
                .concat(getDefaultValue(method))
                .concat(SEMICOLON)
                .concat(NEWLINE)
                .concat(TAB)
                .concat(BLOCK_CLOSED);
    }

    /**
     * Generates {@link String} enumerating {@link Method} exceptions.
     *
     * @param method {@link Method} which exceptions are required
     * @return {@link String} exceptions list
     */
    private static String getMethodExceptions(Method method) {
        Class<?>[] exceptionsTypes = method.getExceptionTypes();
        if (exceptionsTypes.length == 0) {
            return EMPTY;
        }
        return String.join(SPACE,
                THROWS, Arrays.stream(exceptionsTypes)
                        .map(Class::getCanonicalName)
                        .collect(Collectors.joining(COMMA_WITH_SPACE)));
    }

    /**
     * Generates {@link Method} code.
     *
     * @param method {@link Method} to generate body of
     * @return Method implementation body as {@link String}
     */
    private static String generateMethod(Method method) {
        return TAB.concat(getMethodDefinition(method))
                .concat(NEWLINE)
                .concat(getMethodDeclaration(method));
    }

    /**
     * Generates {@link String} of implementations of each final version {@link Method} among given ones.
     *
     * @param token {@link Class} which implementation is required
     * @return {@link String} of source code {@link Method}s implementations.
     */
    private static String generateAllMethods(Class<?> token) {
        return Arrays.stream(token.getMethods())
                .map(NativeCodeImplementor::generateMethod)
                .collect(Collectors.joining(NEWLINE.repeat(2)));
    }

    /**
     * Converts given string to unicode escaping
     *
     * @param line {@link String} to convert
     * @return converted string
     */
    private static String getUnicodeFormat(String line) {
        StringBuilder unicodeString = new StringBuilder();
        for (char c : line.toCharArray()) {
            if (c >= 128) {
//                The X means that it will print an integer, in hexadecimal, large X for large hexadecimal letters
//                The 4 means the number will be printed left justified with at least four digits,
//                print spaces if there is less than four digits
//                The 0 means that if there is less than four digits it will print leading zeroes.
                unicodeString.append(String.format("\\u%04X", (int) c));
            } else {
                unicodeString.append(c);
            }
        }
        return unicodeString.toString();
    }

    /**
     * Generated complete source code of given {@link Class}.
     *
     * @param token {@link Class} which implementation is required
     * @return {@link String} containing complete generated source code
     * @see #getClassHeader(Class)
     * @see #generateMethod(Method)
     */
    public static String getImplementation(Class<?> token) {
        return getUnicodeFormat(getPackage(token))
                .concat(getUnicodeFormat(NEWLINE))
                .concat(getUnicodeFormat(getClassHeader(token)))
                .concat(getUnicodeFormat(SPACE + BLOCK_OPENED))
                .concat(getUnicodeFormat(NEWLINE))
                .concat(getUnicodeFormat(generateAllMethods(token)))
                .concat(getUnicodeFormat(NEWLINE))
                .concat(getUnicodeFormat(BLOCK_CLOSED));
    }

    /**
     * Class implementing Supplier. Helper class to generate enumeration of arguments
     */
    private static class NameGenerator implements Supplier<String> {

        int number = 0;

        @Override
        public String get() {
            return "var" + (number++);
        }
    }
}
