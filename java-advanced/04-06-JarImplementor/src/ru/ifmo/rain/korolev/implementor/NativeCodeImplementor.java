package ru.ifmo.rain.korolev.implementor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;


/**
 * Assisting class to {@link JarImplementor}. Provides tools for necessary operations
 * for implementation source code generation.
 *
 * @author Korolev Daniil
 * @version 239.0
 */

class NativeCodeImplementor {

    /**
     * An open brace constant.
     */
    private static final String OPENED_BRACE = "(";

    /**
     * A closing brace constant.
     */
    private static final String CLOSED_BRACE = ")";

    /**
     * A closing block constant.
     */
    private static final String CLOSED_BLOCK = "}";

    /**
     * An opening block constant.
     */
    private static final String OPENED_BLOCK = "{";

    /**
     * A space constant.
     */
    private static final String SPACE = " ";

    /**
     * An end of expression constant.
     */
    private static final String SEMICOLON = ";";

    /**
     * A system defined line separator constant.
     */
    private static final String NEWLINE = System.lineSeparator();

    /**
     * An empty string constant.
     */
    private static final String EMPTY = "";

    /**
     * A comma constant
     */
    private static final String COMMA = ",";

    /**
     * A tabulation constant.
     */
    private static final String TAB = "\t";

    /**
     * package keyword
     */
    private static final String PACKAGE = "package";

    /**
     * public keyword
     */
    private static final String PUBLIC = "public";

    /**
     * class keyword
     */
    private static final String CLASS = "class";

    /**
     * implements keyword
     */
    private static final String IMPLEMENTS = "implements";

    /**
     * throws keyword
     */
    private static final String THROWS = "throws";
    /**
     * return keyword
     */
    private static final String RETURN = "return";

    /**
     * null keyword
     */
    private static final String NULL = "null";

    /**
     * false keyword
     */
    private static final String FALSE = "false";

    /**
     * 0 string representation
     */
    private static final String ZERO = "0";

    /**
     * An implementation suffix for .java file
     */
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
                .collect(Collectors.joining(COMMA + SPACE));
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
                .concat(OPENED_BRACE + getArguments(method) + CLOSED_BRACE)
                .concat(SPACE)
                .concat(getExceptions(method))
                .concat(SPACE)
                .concat(OPENED_BLOCK);
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
                .concat(CLOSED_BLOCK);
    }

    /**
     * Generates {@link String} enumerating {@link Method} exceptions.
     *
     * @param method {@link Method} which exceptions are required
     * @return {@link String} exceptions list
     */
    private static String getExceptions(Method method) {
        if (method.getExceptionTypes().length != 0) {
            return SPACE + THROWS + SPACE + Arrays.stream(method.getExceptionTypes()).
                    map(Class::getCanonicalName).
                    collect(Collectors.joining(COMMA + SPACE));
        }
        return EMPTY;
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
                .concat(getUnicodeFormat(SPACE + OPENED_BLOCK))
                .concat(getUnicodeFormat(NEWLINE))
                .concat(getUnicodeFormat(generateAllMethods(token)))
                .concat(getUnicodeFormat(NEWLINE))
                .concat(getUnicodeFormat(CLOSED_BLOCK));
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
     * Auxiliary class to generate variables names
     * <p>
     * {@link Supplier}, witch returns uniq names before reset
     */
    private static class NameGenerator implements Supplier<String> {

        /**
         * Current variable name
         */
        static int number = 0;

        /**
         * {@link Supplier} method realisation
         *
         * @return {@link String} next variable name
         */
        @Override
        public String get() {
            return "var" + (number++);
        }
    }
}