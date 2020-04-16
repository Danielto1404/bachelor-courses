package ru.ifmo.rain.korolev.crawler;

import info.kgeorgiy.java.advanced.crawler.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

public class WebCrawler implements Crawler {

    private final ExecutorService downloaders;
    private final ExecutorService extractors;
    private final Downloader downloader;

    private static final String USAGE = "Usage : WebCrawler url [depth [downloads [extractors [perHost]]]]";
    private static final int DEFAULT_VALUE = 1;

    /**
     * @param downloader  {@link Downloader} auxiliary class, which helps to download web-pages
     * @param downloaders number of given downloader threads
     * @param extractors  number of given link extractors threads
     * @param perHost     ---------------------------------------
     */
    public WebCrawler(Downloader downloader, int downloaders, int extractors, int perHost) {
        this.downloaders = Executors.newFixedThreadPool(downloaders);
        this.extractors = Executors.newFixedThreadPool(extractors);
        this.downloader = downloader;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Result download(String url, int depth) {
        final Set<String> usedURL = ConcurrentHashMap.newKeySet();
        final Map<String, IOException> errors = new ConcurrentHashMap<>();

        final Phaser phaser = new Phaser();
        phaser.register();

        usedURL.add(url);
        recursiveCrawl(url, depth, usedURL, errors, phaser);
        phaser.arriveAndAwaitAdvance();

        usedURL.removeAll(errors.keySet());
        return new Result(new ArrayList<>(usedURL), errors);
    }

    private Runnable createExtractTask(final Document document,
                                       final String url,
                                       final int depth,
                                       final Set<String> usedURL,
                                       final Map<String, IOException> errors,
                                       final Phaser phaser) {
        return () -> {
            try {
                for (String link : document.extractLinks()) {
                    if (usedURL.add(link)) {
                        recursiveCrawl(link, depth - 1, usedURL, errors, phaser);
                    }
                }
            } catch (IOException e) {
                errors.put(url, e);
            } finally {
                phaser.arrive();
            }
        };
    }

    private Runnable createDownloadTask(final String url,
                                        final int depth,
                                        final Set<String> usedUrl,
                                        final Map<String, IOException> errors,
                                        final Phaser phaser) {
        return () -> {
            try {
                final Document document = downloader.download(url);
                if (depth != 1) {
                    Runnable extractLinksTask = createExtractTask(document, url, depth, usedUrl, errors, phaser);
                    phaser.register();
                    extractors.submit(extractLinksTask);
                }
            } catch (IOException e) {
                errors.put(url, e);
            } finally {
                phaser.arrive();
            }
        };
    }


    private void recursiveCrawl(final String url,
                                final int depth,
                                final Set<String> usedUrl,
                                final Map<String, IOException> errors,
                                final Phaser phaser) {
        Runnable downloadTask = createDownloadTask(url, depth, usedUrl, errors, phaser);
        phaser.register();
        downloaders.submit(downloadTask);
    }

    private static int getValue(String[] args, int index) {
        try {
            if (args.length > index && args[index] != null) {
                return Integer.parseInt(args[index]);
            } else {
                return DEFAULT_VALUE;
            }
        } catch (NumberFormatException e) {
            return DEFAULT_VALUE;
        }
    }

    private static void printResult(Result result) {
        System.out.println("Successfully downloaded " + result.getDownloaded().size() + " pages: ");
        result.getDownloaded().forEach(System.out::println);

        System.out.println("Not downloaded due to error: " + result.getErrors().size());
        result.getErrors().forEach((msg, e) -> {
            System.out.println("URL: " + msg);
            System.out.println("Error: " + e.getMessage());
        });
    }

    /**
     * @param args needed to be provided: "Usage : WebCrawler url [depth [downloads [extractors [perHost]]]]"
     * @throws IOException in case if error occurred
     */
    public static void main(String[] args) throws IOException {
        if (args.length < 1 || args[0] == null) {
            System.err.println("Wrong arguments. " + USAGE);
        }

        String url = args[0];
        Downloader downloader = new CachingDownloader();

        int depth = getValue(args, 1);
        int downloaders = getValue(args, 2);
        int extractors = getValue(args, 3);
        int perhost = getValue(args, 4);

        WebCrawler crawler = new WebCrawler(downloader, downloaders, extractors, perhost);
        Result result = crawler.download(url, depth);
        printResult(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        downloaders.shutdownNow();
        extractors.shutdownNow();
    }
}