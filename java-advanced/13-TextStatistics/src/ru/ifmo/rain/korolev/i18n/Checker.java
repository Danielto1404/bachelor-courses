package ru.ifmo.rain.korolev.i18n;

public class Checker {

    public static boolean checkArguments(String[] args, int size) {
        if (args == null || args.length != size) {
            return false;
        }
        for (String arg : args) {
            if (arg == null) {
                return false;
            }
        }
        return true;
    }
}
