package ru.ifmo.rain.korolev.hello;

import info.kgeorgiy.java.advanced.hello.HelloServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static ru.ifmo.rain.korolev.hello.Checker.checkArguments;
import static ru.ifmo.rain.korolev.hello.Checker.extractValueFromArguments;
import static ru.ifmo.rain.korolev.hello.DatagramByteBufferManager.extractMessage;
import static ru.ifmo.rain.korolev.hello.DatagramByteBufferManager.fillBuffer;


public class HelloUDPNonblockingServer implements HelloServer {
    private final static int SELECTOR_AWAITING_TIME = 1000;
    private final static int MANAGER_AWAITING_TIME = 10;
    private static final String USAGE = "USAGE: <HelloUDPServer> [port] [threads]";

    private DatagramChannel mainChanel;
    private Selector selector;
    private ExecutorService manager;

    public static class ClientAttachment {
        private static final int MAX_UDP_MESSAGE_SIZE = 1 << 16;
        ByteBuffer buffer;
        SocketAddress address;

        ClientAttachment() {
            buffer = ByteBuffer.allocate(MAX_UDP_MESSAGE_SIZE);
        }
    }

    public void start(int port, int threads) {
        try {
            this.selector = Selector.open();
        } catch (IOException e) {
            System.err.println("Unable to open selector");
            return;
        }

        try {
            mainChanel = DatagramChannel.open();
        } catch (IOException e) {
            System.err.println("Unable to open Datagram channel");
            return;
        }

        InetSocketAddress serverAddress = new InetSocketAddress(port);
        manager = Executors.newSingleThreadExecutor();
        manager.submit(() -> {
            try {
                mainChanel.bind(serverAddress);
                mainChanel.configureBlocking(false);
                mainChanel.register(selector, SelectionKey.OP_READ, new ClientAttachment());

                while (!Thread.interrupted() && selector.isOpen() && mainChanel.isOpen()) {
                    selector.select(SELECTOR_AWAITING_TIME);

                    if (selector.selectedKeys().isEmpty()) {
                        continue;
                    }
                    for (final Iterator<SelectionKey> i = selector.selectedKeys().iterator(); i.hasNext(); ) {
                        final SelectionKey key = i.next();
                        ClientAttachment attachment = (ClientAttachment) key.attachment();
                        if (key.isReadable()) {
                            read(mainChanel, attachment);
                            key.interestOps(SelectionKey.OP_WRITE);
                        } else if (key.isWritable()) {
                            write(mainChanel, attachment);
                            key.interestOps(SelectionKey.OP_READ);
                        }
                        i.remove();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void read(DatagramChannel channel, ClientAttachment attachment) throws IOException {
        attachment.buffer.clear();
        attachment.address = channel.receive(attachment.buffer);
        String requestText = extractMessage(attachment.buffer);
        attachment.buffer.clear();
        fillBuffer(attachment.buffer, "Hello, " + requestText);
    }

    private void write(DatagramChannel channel, ClientAttachment attachment) throws IOException {
        channel.send(attachment.buffer, attachment.address);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        try {
            if (mainChanel != null) mainChanel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (selector != null) selector.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        manager.shutdownNow();
        try {
            boolean finished = manager.awaitTermination(MANAGER_AWAITING_TIME, TimeUnit.SECONDS);
            System.err.println(finished ? "Done" : "Time exceed");
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

            new HelloUDPNonblockingServer().start(port, threads);

        } catch (NumberFormatException e) {
            System.err.println(USAGE);
        }
    }
}