package ru.ifmo.rain.korolev.hello;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class DatagramByteBufferManager {

    public static String extractMessage(final ByteBuffer buffer) {
        buffer.flip();
        return StandardCharsets.UTF_8.decode(buffer).toString();
    }

    public static void fillBuffer(ByteBuffer buffer, final String string) {
        buffer.put(string.getBytes(StandardCharsets.UTF_8));
        buffer.flip();
    }
}