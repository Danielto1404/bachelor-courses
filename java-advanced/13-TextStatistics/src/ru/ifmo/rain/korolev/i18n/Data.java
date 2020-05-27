package ru.ifmo.rain.korolev.i18n;

public class Data<T> {
    private final T value;
    private final String stringRepresentation;
    private final int index;


    public Data(T value, String stringRepresentation, int index) {
        this.value = value;
        this.stringRepresentation = stringRepresentation;
        this.index = index;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return stringRepresentation;
    }

    public int getIndex() {
        return index;
    }
}
