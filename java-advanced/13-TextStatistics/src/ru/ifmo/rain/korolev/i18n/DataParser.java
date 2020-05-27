package ru.ifmo.rain.korolev.i18n;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.*;
import java.util.*;
import java.util.function.Function;

public class DataParser {

    private static final String shortDateParser = "SHORT_DATE_PARSER";
    private static final String mediumDateParser = "MEDIUM_DATE_PARSER";
    private static final String longDateParser = "LONG_DATE_PARSER";
    private static final String fullDateParser = "FULL_DATE_PARSER";
    private static final String numberParser = "NUMBER_PARSER";
    private static final String currencyParser = "CURRENCY_PARSER";

    private final Map<String, Format> parsers;
    private final Locale locale;

    private final Comparator<Data<String>> stringComparator;
    private final Comparator<Data<Number>> numberComparator;

    public DataParser(Locale locale) {
        this.locale = locale;
        this.parsers = Map.of(
                shortDateParser, DateFormat.getDateInstance(DateFormat.SHORT, locale),
                mediumDateParser, DateFormat.getDateInstance(DateFormat.MEDIUM, locale),
                longDateParser, DateFormat.getDateInstance(DateFormat.LONG, locale),
                fullDateParser, DateFormat.getDateInstance(DateFormat.FULL, locale),
                numberParser, NumberFormat.getNumberInstance(locale),
                currencyParser, NumberFormat.getCurrencyInstance(locale)
        );

        stringComparator = (a, b) -> Collator.getInstance(locale)
                .compare(a.getValue(), b.getValue());

        numberComparator = Comparator.comparingDouble(x -> x.getValue().doubleValue());
    }

    public Comparator<Data<String>> getStringComparator() {
        return stringComparator;
    }

    public Comparator<Data<Number>> getNumberComparator() {
        return numberComparator;
    }

    public ArrayList<String> extractLines(Path path) throws FileNotFoundException {
        try {
            return new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new FileNotFoundException("Unable to find input file: " + path.toString());
        }
    }

    private ArrayList<Data<String>> extract(String text,
                                            BreakIterator iterator,
                                            Function<Triple<String, Integer, Integer>, Boolean> condition) {
        ArrayList<Data<String>> values = new ArrayList<>();
        iterator.setText(text);
        int start = iterator.first();
        int end = iterator.next();

        while (end != BreakIterator.DONE) {
            String value = text.substring(start, end);
            if (condition.apply(new Triple<>(value, start, end))) {
                values.add(new Data<>(value.toLowerCase(), value, -1));
            }
            start = end;
            end = iterator.next();
        }
        return values;
    }

    public ArrayList<Data<String>> extractSentences(String text) {
        return extract(text, BreakIterator.getSentenceInstance(locale), x -> Boolean.TRUE);
    }

    public ArrayList<Data<String>> extractWords(String text) {
        return extract(text, BreakIterator.getWordInstance(locale), triple -> {
            String word = triple.getFirst();
            int start = triple.getSecond();
            int end = triple.getThird();
            if (end - start == 1)
                return Character.isLetterOrDigit(word.codePointAt(0));

            return true;
        });
    }

    public Triple<
            ArrayList<Data<Date>>,
            ArrayList<Data<Number>>,
            ArrayList<Data<Number>>
            > extractDateNumberCurrency(String text) {
        ArrayList<Data<Date>> dates = new ArrayList<>();
        ArrayList<Data<Number>> currencies = new ArrayList<>();
        ArrayList<Data<Number>> numbers = new ArrayList<>();
        int pos = 0;
        while (pos < text.length()) {
            Data<Date> date = parseAllDateStyles(text, pos);
            if (date != null) {
                dates.add(date);
                pos = date.getIndex();
                continue;
            }
            Data<Number> currency = parseCurrency(text, pos);
            if (currency != null) {
                currencies.add(currency);
                pos = currency.getIndex();
                continue;
            }
            Data<Number> number = parseNumber(text, pos);
            if (number != null) {
                numbers.add(number);
                pos = number.getIndex();
                continue;
            }
            ++pos;
        }
        return new Triple<>(
                dates,
                currencies,
                numbers);
    }

    public Data<Date> parseAllDateStyles(String text, int pos) {
        String[] styles = new String[]{
                fullDateParser, longDateParser, mediumDateParser, shortDateParser
        };
        ParsePosition position = new ParsePosition(pos);
        Date date;
        for (String style : styles) {
            date = ((DateFormat) parsers.get(style)).parse(text, position);
            if (date != null)
                return new Data<>(date, text.substring(pos, position.getIndex()), position.getIndex());
        }
        return null;
    }

    public Data<Number> parseCurrency(String text, int pos) {
        ParsePosition position = new ParsePosition(pos);
        Number number = ((NumberFormat) parsers.get(currencyParser)).parse(text, position);
        if (number != null) {
            return new Data<>(number, text.substring(pos, position.getIndex()), position.getIndex());
        }
        return null;
    }

    public Data<Number> parseNumber(String text, int pos) {
        ParsePosition position = new ParsePosition(pos);
        Number number = null;
        if (pos == 0 || Character.isWhitespace(text.codePointAt(pos - 1))) {
            number = ((NumberFormat) parsers.get(numberParser)).parse(text, position);
        }
        if (number != null) {
            return new Data<>(number, number.toString(), position.getIndex());
        }
        return null;
    }
}
