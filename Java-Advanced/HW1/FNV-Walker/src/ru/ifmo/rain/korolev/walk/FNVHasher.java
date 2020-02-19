package ru.ifmo.rain.korolev.walk;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

final public class FNVHasher {

    public static int calculateHash(Path filePath) {
        final int FNV_32_PRIME = 0x01000193;
        int hashValue = 0x811c9dc5;         // FNV0 hashValue = 0

        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(filePath))) {
            final int BUFFER_SIZE = 239;
            byte[] buffer = new byte[BUFFER_SIZE];
            int chAmount;

            while ((chAmount = bis.read(buffer)) >= 0) {
                for (int i = 0; i < chAmount; ++i) {
                    hashValue *= FNV_32_PRIME;
                    hashValue ^= Byte.toUnsignedInt(buffer[i]);
                }
            }
            return hashValue;

        } catch (IOException e) {
            return 0;
        }
    }

}
