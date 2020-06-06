package ru.ifmo.rain.korolev.i18n;

import java.util.ResourceBundle;

public class HTMLConverter {

    private final ResourceBundle bundle;
    private final Integer[] counts;
    private final String[] statistics;
    private final String fileName;

    private static final String NL = System.lineSeparator();

    private static final String BR_TAG = "<br>";
    private static final String DOUBLE_BR_TAG = BR_TAG.repeat(2);
    private static final String OPENED_HEADER_TAG = "<h2>";
    private static final String CLOSED_HEADER_TAG = "</h2>";
    private static final String OPENED_P_TAG = "<p>";
    private static final String CLOSED_P_TAG = "</p>";


    public HTMLConverter(String[] statistics, Integer[] counts, String fileName, ResourceBundle bundle) {
        this.statistics = statistics;
        this.fileName = fileName;
        this.bundle = bundle;
        this.counts = counts;
    }

    private String getHeaderHTML(String header, int index) {
        StringBuilder result = new StringBuilder();
        result.append(OPENED_HEADER_TAG).append(header)
                .append(CLOSED_HEADER_TAG)
                .append(NL)
                .append(OPENED_P_TAG)
                .append(NL);
        for (String line : statistics[index].split(NL)) {
            result.append(line)
                    .append(DOUBLE_BR_TAG)
                    .append(NL);
        }
        return result.append(CLOSED_P_TAG).append(NL).toString();
    }

    public String getHTML() {
        return "<html><head><meta charset=\"UTF-8\" />"
                .concat(NL)
                .concat("<h1>")
                .concat(bundle.getString("analyze"))
                .concat(" " + fileName)
                .concat("</h1>")
                .concat(NL)
                .concat(OPENED_HEADER_TAG)
                .concat(bundle.getString("stat.all"))
                .concat(CLOSED_HEADER_TAG)
                .concat(NL)
                .concat(OPENED_P_TAG)
                .concat(bundle.getString("count.sentence") + " " + counts[0])
                .concat(DOUBLE_BR_TAG)
                .concat(bundle.getString("count.lines") + " " + counts[1])
                .concat(DOUBLE_BR_TAG)
                .concat(bundle.getString("count.words") + " " + counts[2])
                .concat(DOUBLE_BR_TAG)
                .concat(bundle.getString("count.numbers") + " " + counts[3])
                .concat(DOUBLE_BR_TAG)
                .concat(bundle.getString("count.currency") + " " + counts[4])
                .concat(DOUBLE_BR_TAG)
                .concat(bundle.getString("count.dates") + " " + counts[5])
                .concat(DOUBLE_BR_TAG).concat(CLOSED_P_TAG).concat(NL)
                .concat(getHeaderHTML(bundle.getString("stat.sentence"), 0))
                .concat(getHeaderHTML(bundle.getString("stat.lines"), 1))
                .concat(getHeaderHTML(bundle.getString("stat.words"), 2))
                .concat(getHeaderHTML(bundle.getString("stat.numbers"), 3))
                .concat(getHeaderHTML(bundle.getString("stat.currency"), 4))
                .concat(getHeaderHTML(bundle.getString("stat.dates"), 5));
    }
}
