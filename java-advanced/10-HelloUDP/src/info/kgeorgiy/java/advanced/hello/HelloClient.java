package info.kgeorgiy.java.advanced.hello;

public interface HelloClient {
    /**
     * Runs Hello client.
     *
     * @param host     server host
     * @param port     server port
     * @param prefix   request prefix
     * @param threads  number of request threads
     * @param requests number of requests per thread.
     */
    void run(String host, int port, String prefix, int threads, int requests);
}
