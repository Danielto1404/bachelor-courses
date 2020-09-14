package utils.helpers;

public class CharFrequencyPair {
    private final char symbol;
    private final double frequency;

    public CharFrequencyPair(char symbol, double frequency) {
        this.symbol = symbol;
        this.frequency = frequency;
    }

    public double getFrequency() {
        return frequency;
    }

    public char getSymbol() {
        return symbol;
    }
}
