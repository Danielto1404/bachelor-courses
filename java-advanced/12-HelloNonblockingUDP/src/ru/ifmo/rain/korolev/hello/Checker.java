package ru.ifmo.rain.korolev.hello;

public class Checker {

    public static boolean checkArguments(String[] args, int size, String usage) {
        if (args == null || args.length != size) {
            System.err.println(usage);
            return false;
        }
        for (String arg : args) {
            if (arg == null) {
                System.err.println("Expected non-null argument");
                System.err.println(usage);
                return false;
            }
        }
        return true;
    }

    public static int extractValueFromArguments(String[] args, int index) {
        try {
            return Integer.parseInt(args[index]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Argument must be a number");
        }
    }
}
