package ru.ifmo.rain.korolev.hello;

import info.kgeorgiy.java.advanced.hello.HelloClient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

import static ru.ifmo.rain.korolev.hello.Checker.checkArguments;
import static ru.ifmo.rain.korolev.hello.Checker.extractValueFromArguments;
import static ru.ifmo.rain.korolev.hello.DatagramByteBufferManager.extractMessage;
import static ru.ifmo.rain.korolev.hello.DatagramByteBufferManager.fillBuffer;

public class HelloUDPNonblockingClient implements HelloClient {
    private static final int MAX_UDP_MESSAGE_SIZE = 1 << 16;
    private static final int AWAITING_TIME = 100;
    private static final String USAGE =
            "Usage: <HelloUDPClient> [host] [port] [prefix] [threads amount] [query amount for each thread]";


    public static class DatagramAttachment {

        private final int chanelIndex;
        private int requestIndex;

        public void incrementRequestIndex() {
            ++requestIndex;
        }

        public int getRequestIndex() {
            return requestIndex;
        }

        public int getChanelIndex() {
            return chanelIndex;
        }

        public DatagramAttachment(int chanelIndex) {
            this.chanelIndex = chanelIndex;
            this.requestIndex = 0;
        }
    }

    private static String makeRequest(String prefix, int thread, int request) {
        return prefix + thread + "_" + request;
    }

    private static boolean checkArriving(String response, String request) {
        return response.equals("Hello, " + request);
    }

    private static void changeMode(DatagramChannel channel, SelectionKey key, int mode) {
        if (channel.isOpen()) {
            key.interestOps(mode);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(String host, int port, String prefix, int threads, int requests) {
        InetSocketAddress serverAddress = new InetSocketAddress(host, port);
        try (Selector selector = Selector.open()) {

            for (int i = 0; i < threads; ++i) {
                try {
                    DatagramChannel channel = DatagramChannel.open();
                    channel.configureBlocking(false);
                    channel.connect(serverAddress);
                    channel.register(selector, SelectionKey.OP_WRITE, new DatagramAttachment(i));
                } catch (IOException e) {
                    System.err.println("Unable to open Datagram channel");
                }
            }

            int openedChannels = threads;
            ByteBuffer buffer = ByteBuffer.allocate(MAX_UDP_MESSAGE_SIZE);
            while (openedChannels > 0) {
                selector.select(AWAITING_TIME);
                if (selector.selectedKeys().isEmpty()) {
                    selector.keys().forEach(key -> key.interestOps(SelectionKey.OP_WRITE));
                    continue;
                }

                for (final Iterator<SelectionKey> i = selector.selectedKeys().iterator(); i.hasNext(); ) {
                    final SelectionKey key = i.next();
                    final DatagramChannel channel = (DatagramChannel) key.channel();
                    final DatagramAttachment attachment = (DatagramAttachment) key.attachment();
                    final String requestText = makeRequest(prefix,
                            attachment.getChanelIndex(),
                            attachment.getRequestIndex());

                    buffer.clear();
                    if (key.isReadable()) {
                        try {
                            channel.read(buffer);
                            String response = extractMessage(buffer);
                            if (checkArriving(response, requestText)) {
                                attachment.incrementRequestIndex();
                            }
                            if (attachment.getRequestIndex() == requests) {
                                channel.close();
                                --openedChannels;
                            }
                            changeMode(channel, key, SelectionKey.OP_WRITE);
                        } catch (IOException e) {
                            channel.close();
                        }
                    } else if (key.isWritable()) {
                        fillBuffer(buffer, requestText);
                        try {
                            channel.write(buffer);
                        } catch (IOException e) {
                            channel.close();
                        }
                        changeMode(channel, key, SelectionKey.OP_READ);
                    }
                    i.remove();
                }
            }
        } catch (IOException e) {
            System.err.println("Unable to open selector");
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
            new HelloUDPNonblockingClient().run(host, port, prefix, threads, requests);
        } catch (NumberFormatException e) {
            System.err.println(USAGE);
        }
    }
}
