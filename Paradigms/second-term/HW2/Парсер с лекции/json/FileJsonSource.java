package json;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class FileJsonSource extends JsonSource {
    private final Reader reader;

    public FileJsonSource(final String fileName) throws JsonException {
        try {
            reader = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8));
        } catch (final IOException e) {
            throw error("Error opening input file '%s': %s", fileName, e.getMessage());
        }
    }

    @Override
    protected char readChar() throws IOException {
        final int read = reader.read();
        return read == -1 ? END : (char) read;
    }
}
