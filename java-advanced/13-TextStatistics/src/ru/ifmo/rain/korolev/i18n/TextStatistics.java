package ru.ifmo.rain.korolev.i18n;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import static ru.ifmo.rain.korolev.i18n.Checker.checkArguments;

public class TextStatistics {
    private static final String bundlePath = "ru.ifmo.rain.korolev.i18n.LocaleResourceBundle";

    private static Locale extractLocale(String locale) {
        String[] languageCountryVariant = locale.split("_");
        if (languageCountryVariant.length == 3) {
            return new Locale(languageCountryVariant[0], languageCountryVariant[1], languageCountryVariant[2]);
        } else if (languageCountryVariant.length == 2) {
            return new Locale(languageCountryVariant[0], languageCountryVariant[1]);
        } else {
            return new Locale(languageCountryVariant[0]);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        if (!checkArguments(args, 4)) {
            System.err.println("Wrong arguments!");
            return;
        }
        Locale inputLocale = extractLocale(args[0]);
        Locale outputLocale = extractLocale(args[1]);
        ResourceBundle bundle = ResourceBundle.getBundle(bundlePath, outputLocale);
        Path inputPath, outputPath;
        try {
            inputPath = Paths.get(args[2]);
        } catch (InvalidPathException e) {
            System.err.println(bundle.getString("invalid.input.path") + ": " + args[2]);
            return;
        }
        try {
            outputPath = Paths.get(args[3]);
        } catch (InvalidPathException e) {
            System.err.println(bundle.getString("invalid.output.path") + ": " + args[3]);
            return;
        }
        StatisticsCalculator calculator = new StatisticsCalculator(bundle);
        DataParser parser = new DataParser(inputLocale);

        String[] lines = parser.extractLines(inputPath).toArray(new String[0]);

        ArrayList<Integer> counts = new ArrayList<>();

        HTMLConverter converter = new HTMLConverter(
                calculator.getAllStatistics(String.join("\n", lines), inputLocale, counts),
                counts.toArray(new Integer[0]),
                inputPath.toString(),
                bundle);

        String html = converter.getHTML();

        try (BufferedWriter bw = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)) {
            bw.write(html);
        } catch (IOException e) {
            System.err.println(bundle.getString("unable.write.to.file") + ": " + outputPath.toString());
        }
    }
}
