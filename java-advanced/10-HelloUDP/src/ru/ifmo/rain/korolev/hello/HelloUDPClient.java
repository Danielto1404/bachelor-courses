package ru.ifmo.rain.korolev.hello;

import info.kgeorgiy.java.advanced.hello.HelloClient;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static ru.ifmo.rain.korolev.hello.Checker.checkArguments;
import static ru.ifmo.rain.korolev.hello.Checker.extractValueFromArguments;
import static ru.ifmo.rain.korolev.hello.DatagramPacketManager.*;

public class HelloUDPClient implements HelloClient {
    private static final int RECEIVE_WAITING_TIME = 500;
    private static final int AWAITING_TIME = 100_000;
    private static final String USAGE =
            "Usage: <HelloUDPClient> [host] [port] [prefix] [threads amount] [query amount for each thread]";


    private static String makeRequest(String prefix, int thread, int request) {
        return prefix + thread + "_" + request;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(String host, int port, String prefix, int threads, int requests) {
        final InetAddress ip;
        try {
            ip = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            System.err.println("Can't resolve host's name");
            return;
        }

        final ExecutorService workers = Executors.newFixedThreadPool(threads);
        final SocketAddress socketAddress = new InetSocketAddress(ip, port);

        for (int i = 0; i < threads; ++i) {
            int threadIndex = i;
            workers.submit(() -> request(socketAddress, prefix, threadIndex, requests));
        }

        workers.shutdown();

        try {
            boolean closed = workers.awaitTermination(AWAITING_TIME, TimeUnit.SECONDS);
            System.err.println(closed ? "Done" : "Timeout");
        } catch (InterruptedException e) {
            System.err.println("Interrupted" + e.getCause());
        }
    }

    private static boolean checkArriving(String response, String request) {
        return response.equals("Hello, " + request);
    }

    private static void request(final SocketAddress serverAddress,
                                final String prefix,
                                final int threadIndex,
                                final int requests) {

        try (final DatagramSocket socket = new DatagramSocket()) {

            socket.setSoTimeout(RECEIVE_WAITING_TIME);
            final DatagramPacket request = makeRequestDP(serverAddress);
            final DatagramPacket response = makeReceiveDP(socket);

            for (int requestIndex = 0; requestIndex < requests; ++requestIndex) {

                final String requestText = makeRequest(prefix, threadIndex, requestIndex);
                request.setData(stringToBytes(requestText));

                while (!socket.isClosed()) {
                    try {
                        socket.send(request);
                        System.out.println("Request: " + requestText);
                        socket.receive(response);
                    } catch (IOException e) {
                        System.err.println("Failed to perform a request / get a response: " + e.getMessage());
                        continue;
                    }

                    final String responseText = extractMessage(response);
                    System.out.println("Response: " + responseText);
                    if (checkArriving(responseText, requestText)) {
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * Runs the client:
     * <p>
     * Usage: <HelloUDPClient> [host] [port] [prefix] [threads amount] [query amount for each thread]
     */
    public static void main(String[] args) {
        if (!checkArguments(args, 5, USAGE)) {
            return;
        }
        try {
            String host = args[0];
            int port = extractValueFromArguments(args, 1);
            String prefix = args[2];
            int threads = extractValueFromArguments(args, 3);
            int requests = extractValueFromArguments(args, 4);

            new HelloUDPClient().run(host, port, prefix, threads, requests);

        } catch (NumberFormatException e) {
            System.err.println(USAGE);
        }
    }
}