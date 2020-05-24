package ru.ifmo.rain.korolev.hello;

import info.kgeorgiy.java.advanced.hello.HelloServer;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.*;

import static ru.ifmo.rain.korolev.hello.DatagramPacketManager.*;
import static ru.ifmo.rain.korolev.hello.Checker.*;


public class HelloUDPServer implements HelloServer {
    private final static int AWAITING_TIME = 10;
    private static final String USAGE = "USAGE: <HelloUDPServer> [port] [threads]";

    private DatagramSocket serverSocket = null;
    private ExecutorService workers = null;
    private ExecutorService manager = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(int port, int threads) {
        try {
            serverSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            System.err.println("Datagram Socket exception appeared: " + e.getMessage());
            return;
        }

        workers = Executors.newFixedThreadPool(threads);

        manager = Executors.newSingleThreadExecutor();
        manager.submit(() -> {
            while (!serverSocket.isClosed()) {
                try {
                    final DatagramPacket request = makeReceiveDP(serverSocket);
                    serverSocket.receive(request);
                    workers.submit(() -> respond(request));
                } catch (IOException e) {
                    System.err.println("Unable to receive: " + e.getMessage());
                }
            }
        });
    }

    private void respond(final DatagramPacket request) {
        final DatagramPacket respond = makeRequestDP(request.getSocketAddress());
        respond.setData(stringToBytes("Hello, " + extractMessage(request)));
        try {
            serverSocket.send(respond);;
        } catch (IOException e) {
            System.err.println("Response exception occurred: " + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        serverSocket.close();
        workers.shutdownNow();
        manager.shutdownNow();
        try {
            workers.awaitTermination(AWAITING_TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("Interrupted" + e.getCause());
        }
    }


    /**
     * Runs a server:
     * <p>
     * USAGE: <HelloUDPServer> [port] [threads]
     */
    public static void main(String[] args) {
        if (!checkArguments(args, 2, USAGE)) {
            return;
        }
        try {
            int port = extractValueFromArguments(args, 0);
            int threads = extractValueFromArguments(args, 1);

            new HelloUDPServer().start(port, threads);

        } catch (NumberFormatException e) {
            System.err.println(USAGE);
        }
    }
}