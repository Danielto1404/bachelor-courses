package ru.ifmo.rain.korolev.hello;


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class DatagramPacketManager {

    public static DatagramPacket makeReceiveDP(final DatagramSocket socket) throws SocketException {
        final int bufferSize = socket.getReceiveBufferSize();
        return new DatagramPacket(new byte[bufferSize], bufferSize);
    }

    public static DatagramPacket makeRequestDP(final SocketAddress address) {
        return new DatagramPacket(new byte[0], 0, address);
    }

    public static String extractMessage(final DatagramPacket packet) {
        return new String(packet.getData(),
                packet.getOffset(),
                packet.getLength(),
                StandardCharsets.UTF_8);
    }

    public static byte[] stringToBytes(final String string) {
        return string.getBytes(StandardCharsets.UTF_8);
    }
}