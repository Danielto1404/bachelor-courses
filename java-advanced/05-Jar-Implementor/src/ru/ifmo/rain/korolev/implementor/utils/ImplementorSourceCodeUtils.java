package ru.ifmo.rain.korolev.implementor.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ImplementorSourceCodeUtils {

    private static final String EMPTY = "";
    private static final String SPACE = " ";
    private static final String COMMA_WITH_SPACE = "," + SPACE;
    private static final String TAB = "\t";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String SEMICOLON = ";";
    private static final String LBRACE = "(";
    private static final String RBRACE = ")";
    private static final String BLOCK_OPENED = "{";
    private static final String BLOCK_CLOSED = "}";

    private static final String PACKAGE = "package";
    private static final String CLASS = "class";
    private static final String IMPLEMENTS = "implements";
    private static final String THROWS = "throws";
    private static final String RETURN = "return";

    private static final String NULL = "null";
    private static final String FALSE = "false";
    private static final String ZERO = "0";

    private static final String IMPL_SUFFIX = "Impl";

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
                .map(argumentType -> String.join(SPACE,
                        argumentType.getCanonicalName(),
                        nameGenerator.get()))
                .collect(Collectors.joining(COMMA_WITH_SPACE));
    }

    private static String getThrowingExceptions(Method method) {
        Class<?>[] exceptionTypes = method.getExceptionTypes();
        if (exceptionTypes.length == 0) {
            return EMPTY;
        }
        return String.join(
                SPACE,
                THROWS,
                Arrays.stream(exceptionTypes)
                .map(Class::getCanonicalName)
                .collect(Collectors.joining(COMMA_WITH_SPACE)));
    }

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
                BLOCK_OPENED);
    }

    private static String generateMethodDefinition(Method method) {

        String modifiers = getMethodModifiers(method);
        String methodName = method.getName();
        String returnType = method.getReturnType().getCanonicalName();
        String arguments = getArguments(method);
        String exceptions = getThrowingExceptions(method);

        return String.join(SPACE,
                modifiers,
                returnType,
                methodName,
                LBRACE + arguments + RBRACE,
                exceptions,
                BLOCK_OPENED);
    }

    private static String generateMethod(Method method) {
        return String.join(LINE_SEPARATOR,
                makeIndent(1, generateMethodDefinition(method)),
                makeIndent(2, generateMethodBody(method)),
                makeIndent(1, BLOCK_CLOSED)
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

    public static String generateSourceCode(Class<?> token) {
        return String.join(LINE_SEPARATOR,
                generatePackageLine(token),
                LINE_SEPARATOR,
                generateClassOpeningLine(token),
                generateAllMethods(token),
                BLOCK_CLOSED);
    }
}