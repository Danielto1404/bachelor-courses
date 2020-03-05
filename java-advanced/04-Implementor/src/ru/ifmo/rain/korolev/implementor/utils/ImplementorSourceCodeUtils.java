package ru.ifmo.rain.korolev.implementor.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ImplementorSourceCodeUtils {

    /**
     * Key code tokens
     */
    private static final String EMPTY = "";
    private static final String SPACE = " ";
    private static final String COMMA = ", ";
    private static final String TAB = "\t";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String SEMICOLON = ";";
    private static final String BRACES_OPEN = "(";
    private static final String BRACES_CLOSE = ")";
    private static final String BLOCK_OPEN = "{";
    private static final String BLOCK_CLOSE = "}";

    private static final String PACKAGE = "package";
    private static final String CLASS = "class";
    private static final String IMPLEMENTS = "implements";
    private static final String THROWS = "throws";
    private static final String RETURN = "return";

    private static final String NULL = "null";
    private static final String FALSE = "false";
    private static final String ZERO = "0";

    private static final String IMPL_SUFFIX = "Impl";

    /**
     * Code generation auxiliary members
     */

    private static String makeIndent(int cnt, String... lines) {
        return TAB.repeat(cnt) + String.join(EMPTY, lines);
    }

    private static String getDefaultValue(Class<?> token) {
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

    private static String getModifiers(int modifiers) {
        return Modifier.toString(modifiers & ~Modifier.ABSTRACT);
    }

    private static String getClassModifiers(Class<?> token) {
        return getModifiers(token.getModifiers() & ~Modifier.INTERFACE);
    }

    private static String getMethodModifiers(Method method) {
        return getModifiers(method.getModifiers() & ~Modifier.TRANSIENT);
    }

    private static String getClassImplementationName(Class<?> token) {
        return token.getSimpleName() + IMPL_SUFFIX;
    }

    private static String getArguments(Method method) {
        ArgumentNameGenerator nameGenerator = new ArgumentNameGenerator();
        return Arrays
                .stream(method.getParameterTypes())
                .map(type -> String.join(SPACE,
                        type.getCanonicalName(),
                        nameGenerator.get()))
                .collect(Collectors.joining(COMMA));
    }

    private static String getThrowingExceptions(Method method) {
        Class<?>[] exceptionTypes = method.getExceptionTypes();
        if (exceptionTypes.length == 0) {
            return EMPTY;
        }
        return String.join(
                SPACE,
                THROWS,
                Arrays.stream(method.getExceptionTypes())
                        .map(Class::getCanonicalName)
                        .collect(Collectors.joining(COMMA + SPACE)));
    }

    /**
     * Outer class declaration
     */
    private static String generatePackageLine(Class<?> token) {
        Package pkg = token.getPackage();
        return (pkg == null) ? EMPTY : String.join(
                SPACE,
                PACKAGE,
                pkg.getName(),
                SEMICOLON);
    }

    private static String generateClassOpeningLine(Class<?> token) {
        return String.join(
                SPACE,
                getClassModifiers(token),
                CLASS,
                getClassImplementationName(token),
                IMPLEMENTS,
                token.getCanonicalName(),
                BLOCK_OPEN);
    }

    /**
     * Functions commonly used for constructors and methods
     */
    private static String generateMethodOpeningLine(Method method) {

        String modifiers = getMethodModifiers(method);
        String methodName = method.getName();
        String returnType = method.getReturnType().getCanonicalName();
        String arguments = getArguments(method);
        String exceptions = getThrowingExceptions(method);

        return String.join(SPACE,
                modifiers,
                returnType,
                methodName,
                BRACES_OPEN,
                arguments,
                BRACES_CLOSE,
                exceptions,
                BLOCK_OPEN);
    }

    private static String generateMethod(Method method) {
        return String.join(LINE_SEPARATOR,
                makeIndent(1, generateMethodOpeningLine(method)),
                makeIndent(2, generateMethodBody(method)),
                makeIndent(1, BLOCK_CLOSE)
        );
    }

    private static String generateMethodBody(Method method) {
        return String.join(SPACE, RETURN, getDefaultValue(method.getReturnType()), SEMICOLON);
    }

    private static String generateAllMethods(Class<?> token) {
        return Arrays.stream(token.getMethods())
                .map(ImplementorSourceCodeUtils::generateMethod)
                .collect(Collectors.joining(LINE_SEPARATOR));
    }

    /**
     * Method to collect complete source code from parts
     */
    public static String generateSourceCode(Class<?> token) {
        return String.join(LINE_SEPARATOR,
                generatePackageLine(token),
                LINE_SEPARATOR,
                generateClassOpeningLine(token),
                generateAllMethods(token),
                BLOCK_CLOSE);
    }
}