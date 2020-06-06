package ru.ifmo.rain.korolev.i18n;

import java.util.*;
import java.util.stream.Collectors;

public class StatisticsCalculator {
    private final ResourceBundle bundle;
    private static final String[] bundleKeys = {
            "count",
            "min",
            "max",
            "minLength",
            "maxLength",
            "averageLength"
    };

    private static final String LINE_TYPE = "lines";
    private static final String SENTENCE_TYPE = "sentence";
    private static final String WORD_TYPE = "words";
    private static final String DATE_TYPE = "dates";
    private static final String CURRENCY_TYPE = "currency";
    private static final String NUMBER_TYPE = "numbers";

    private static final String EMPTY_DATA = "empty.data";

    public StatisticsCalculator(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    private <T> String[] calculateStatisticsForType(ArrayList<Data<T>> values,
                                                    Comparator<Data<T>> comparator,
                                                    ArrayList<Integer> counts) {
        TreeSet<Data<T>> unique = new TreeSet<>(comparator);
        unique.addAll(values);

        values.sort(Comparator.comparingInt(data -> data.toString().length()));

        Data<T> firstByLength = values.get(0);
        Data<T> lastByLength = values.get(values.size() - 1);
        counts.add(values.size());
        return new String[]{
                String.format("%d (%d %s)", values.size(), unique.size(),
                        bundle.getString("unique")),
                unique.first().toString().trim(),
                unique.last().toString().trim(),
                String.format("%d (%s)", firstByLength.toString().trim().length(), firstByLength.toString().trim()),
                String.format("%d (%s)", lastByLength.toString().trim().length(), lastByLength.toString().trim()),
                String.valueOf(values.stream().mapToInt(data -> data.toString().trim()
                        .length())
                        .average()
                        .orElse(0))
        };
    }

    public <T> String getStatisticsForValue(ArrayList<Data<T>> values,
                                            Comparator<Data<T>> comparator,
                                            String valueType,
                                            ArrayList<Integer> counts) {
        StringBuilder statistics = new StringBuilder();
        if (values.isEmpty()) {
            counts.add(0);
            statistics.append(bundle.getString(EMPTY_DATA)).append(System.lineSeparator());
        } else {
            String[] results = calculateStatisticsForType(values, comparator, counts);
            statistics = new StringBuilder();
            for (int i = 0; i < results.length; ++i) {
                statistics.append(bundle.getString(bundleKeys[i] + "." + valueType))
                        .append(" ")
                        .append(results[i])
                        .append(System.lineSeparator());
            }
        }
        return statistics.append(System.lineSeparator()).toString();
    }

    public String[] getAllStatistics(String lines,
                                     Locale inputLocale,
                                     ArrayList<Integer> counts) {

        StatisticsCalculator calculator = new StatisticsCalculator(bundle);
        DataParser parser = new DataParser(inputLocale);
        String text = String.join(" ", lines.split("\n"));

        ArrayList<Data<String>> linesData = Arrays.stream(lines.split("\n"))
                .map(line -> new Data<>(line.toLowerCase(), line, -1))
                .collect(Collectors.toCollection(ArrayList::new));

        Triple<ArrayList<Data<Date>>, ArrayList<Data<Number>>, ArrayList<Data<Number>>> triple =
                parser.extractDateNumberCurrency(text);

        ArrayList<Data<String>> sentences = parser.extractSentences(text);
        ArrayList<Data<String>> words = parser.extractWords(text);
        ArrayList<Data<Date>> dates = triple.getFirst();
        ArrayList<Data<Number>> currencies = triple.getSecond();
        ArrayList<Data<Number>> numbers = triple.getThird();

        String sentencesStatistics = calculator.getStatisticsForValue(sentences, parser.getStringComparator(), SENTENCE_TYPE, counts);
        String linesStatistics = calculator.getStatisticsForValue(linesData, parser.getStringComparator(), LINE_TYPE, counts);
        String wordsStatistics = calculator.getStatisticsForValue(words, parser.getStringComparator(), WORD_TYPE, counts);
        String numbersStatistics = calculator.getStatisticsForValue(numbers, parser.getNumberComparator(), NUMBER_TYPE, counts);
        String currenciesStatistics = calculator.getStatisticsForValue(currencies, parser.getNumberComparator(), CURRENCY_TYPE, counts);
        String datesStatistics = calculator.getStatisticsForValue(dates, Comparator.comparing(Data::getValue), DATE_TYPE, counts);

        return new String[]{
                sentencesStatistics, linesStatistics, wordsStatistics, numbersStatistics, currenciesStatistics, datesStatistics
        };

    }
}
