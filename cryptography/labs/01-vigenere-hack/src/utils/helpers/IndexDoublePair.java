package utils.helpers;

public class IndexDoublePair {

    private final Integer index;
    private final Double number;

    public IndexDoublePair(Integer index, Double number) {
        this.index = index;
        this.number = number;
    }

    public Integer getIndex() {
        return index;
    }

    public Double getValue() {
        return number;
    }

    @Override
    public String toString() {
        return "{" +
                " index = " + index +
                ", number = " + number +
                " }";
    }
}
