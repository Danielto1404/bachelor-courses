package ru.ifmo.rain.korolev.i18n;

import java.util.*;

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
}
