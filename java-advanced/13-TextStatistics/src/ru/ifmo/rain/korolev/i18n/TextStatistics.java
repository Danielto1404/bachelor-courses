package ru.ifmo.rain.korolev.i18n;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static ru.ifmo.rain.korolev.i18n.Checker.checkArguments;

public class TextStatistics {
    private static final String bundlePath = "ru.ifmo.rain.korolev.i18n.LocaleResourceBundle";

    private static final String LINE_TYPE = "lines";
    private static final String SENTENCE_TYPE = "sentence";
    private static final String WORD_TYPE = "words";
    private static final String DATE_TYPE = "dates";
    private static final String CURRENCY_TYPE = "currency";
    private static final String NUMBER_TYPE = "numbers";

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
        System.out.println(inputLocale);
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
        StatisticsCalculator creator = new StatisticsCalculator(bundle);
        DataParser parser = new DataParser(inputLocale);

        String[] lines = parser.extractLines(inputPath).toArray(new String[0]);
        String text = String.join(" ", lines);

        ArrayList<Data<String>> linesData = Arrays
                .stream(lines)
                .map(line -> new Data<>(line.toLowerCase(), line, -1))
                .collect(Collectors.toCollection(ArrayList::new));

        Triple<ArrayList<Data<Date>>, ArrayList<Data<Number>>, ArrayList<Data<Number>>> triple =
                parser.extractDateNumberCurrency(text);

        ArrayList<Data<String>> sentences = parser.extractSentences(text);
        ArrayList<Data<String>> words = parser.extractWords(text);
        ArrayList<Data<Date>> dates = triple.getFirst();
        ArrayList<Data<Number>> currencies = triple.getSecond();
        ArrayList<Data<Number>> numbers = triple.getThird();
        ArrayList<Integer> counts = new ArrayList<>();

        String linesStatistics = creator.getStatisticsForValue(linesData, parser.getStringComparator(), LINE_TYPE, counts);
        String sentencesStatistics = creator.getStatisticsForValue(sentences, parser.getStringComparator(), SENTENCE_TYPE, counts);
        String wordsStatistics = creator.getStatisticsForValue(words, parser.getStringComparator(), WORD_TYPE, counts);
        String numbersStatistics = creator.getStatisticsForValue(numbers, parser.getNumberComparator(), NUMBER_TYPE, counts);
        String currenciesStatistics = creator.getStatisticsForValue(currencies, parser.getNumberComparator(), CURRENCY_TYPE, counts);
        String datesStatistics = creator.getStatisticsForValue(dates, Comparator.comparing(Data::getValue), DATE_TYPE, counts);

        HTMLConverter converter = new HTMLConverter(
                new String[]{
                        sentencesStatistics,
                        linesStatistics,
                        wordsStatistics,
                        numbersStatistics,
                        currenciesStatistics,
                        datesStatistics
                },
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
