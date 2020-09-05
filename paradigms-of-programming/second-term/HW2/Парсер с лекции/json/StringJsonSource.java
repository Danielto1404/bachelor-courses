package json;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class StringJsonSource extends JsonSource {
    private final String data;

    public StringJsonSource(final String data) throws JsonException {
        this.data = data + END;
    }

    @Override
    protected char readChar() {
        return data.charAt(pos);
    }
}
