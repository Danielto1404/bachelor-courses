package ru.ifmo.rain.korolev.implementor.utils;

import java.util.function.Supplier;

public class ArgumentNameGenerator implements Supplier<String> {

    static final String NAME_PREFIX = "DANIEL_239";
    int id = 0;

    @Override
    public String get() {
        return NAME_PREFIX + id++;
    }
}
