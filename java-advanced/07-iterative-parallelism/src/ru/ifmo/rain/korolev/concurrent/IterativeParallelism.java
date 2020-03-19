package ru.ifmo.rain.korolev.concurrent;

import info.kgeorgiy.java.advanced.concurrent.ScalarIP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Implementation of {@link ScalarIP} interface using iterative parallelism.
 *
 * @author Korolev Daniil
 * @version 239.0
 */
public class IterativeParallelism implements ScalarIP {

    /**
     * Creates an almost uniform distribution of tasks performed on a given number of threads {@link Thread}
     *
     * @param <T>     Associated type for task.
     * @param threads Number of threads to use.
     * @param values  List {@link List} of tasks to be completed.
     * @return {@link List} returns list of tasks distributed on given number of threads {@link Thread}
     */
    private <T> List<Stream<T>> makeDistributionForThreads(int threads, List<T> values) {

        if (threads <= 0) {
            throw new IllegalArgumentException("Number of threads must be positive");
        }
        threads = Math.max(1, Math.min(threads, values.size()));
        final List<Stream<T>> distribution = new ArrayList<>();
        final int groupSize = values.size() / threads;
        int extraTasks = values.size() % threads;
        for (int i = 0, end = 0; i < threads; i++) {
            int begin = end;
            end = begin + groupSize + (extraTasks > 0 ? 1 : 0);
            extraTasks--;
            distribution.add(values.subList(begin, end).stream());
        }

        return distribution;
    }

    /**
     * @param threads {@link List} of threads to join.
     * @throws InterruptedException throws in case some thread didn't join.
     */
    private static void joinTasks(final List<Thread> threads) throws InterruptedException {
        InterruptedException exceptions = new InterruptedException();
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                exceptions.addSuppressed(e);
            }
        }
        if (exceptions.getSuppressed().length != 0) {
            throw exceptions;
        }
    }
    
    private <T, R> R baseTask(int threads, List<T> list, Function<Stream<T>, R> tasksFunction,
                              Function<Stream<R>, R> collector) throws InterruptedException {

        List<Stream<T>> tasksDistributions = makeDistributionForThreads(threads, list);
        threads = tasksDistributions.size();
        List<R> tasksResults = new ArrayList<>(Collections.nCopies(threads, null));
        List<Thread> workers = new ArrayList<>();
        for (int index = 0; index < threads; index++) {
            final int thisIndex = index;
            Thread thread = new Thread(
                    () -> tasksResults.set(thisIndex, tasksFunction.apply(tasksDistributions.get(thisIndex))));
            workers.add(thread);
            thread.start();
        }
        joinTasks(workers);
        return collector.apply(tasksResults.stream());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T maximum(int threads, List<? extends T> values, Comparator<? super T> comparator)
            throws InterruptedException {
        return baseTask(threads, values,
                stream -> stream.max(comparator).orElseThrow(),
                stream -> stream.max(comparator).orElseThrow());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T minimum(int threads, List<? extends T> values, Comparator<? super T> comparator)
            throws InterruptedException {
        return maximum(threads, values, comparator.reversed());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> boolean all(int threads, List<? extends T> values, Predicate<? super T> predicate)
            throws InterruptedException {
        return baseTask(threads, values,
                stream -> stream.allMatch(predicate),
                stream -> stream.allMatch(Boolean::booleanValue));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> boolean any(int threads, List<? extends T> values, Predicate<? super T> predicate)
            throws InterruptedException {
        return !all(threads, values, predicate.negate());
    }
}
