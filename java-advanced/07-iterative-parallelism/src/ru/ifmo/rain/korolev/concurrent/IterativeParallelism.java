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
     * @param threads Number of threads to use.
     * @param tasks   List {@link List} of tasks to be completed.
     * @param <T>     Associated type for task.
     * @return {@link List} returns list of tasks distributed on given number of threads {@link Thread}
     */
    private <T> List<List<T>> makeDistributionForThreads(int threads, List<T> tasks) {
        if (threads <= 0) {
            throw new IllegalArgumentException("Threads quantity must be greater than 0");
        }

        threads = Math.max(threads, Math.min(tasks.size(), threads));
        int taskSize = tasks.size() / threads;
        int remainder = tasks.size() % threads;
        List<List<T>> distribution = new ArrayList<>();

        int taskIndex = 0;
        for (int thread = 0; thread < threads; thread++) {
            int currentTaskSize = taskSize + (thread < remainder ? 1 : 0);
            if (currentTaskSize > 0) {
                distribution.add(tasks.subList(taskIndex, taskIndex + currentTaskSize));
            }
            taskIndex += currentTaskSize;
        }

        return distribution;
    }

    private <T, B, R> R runIP(int threads, List<T> list, Function<Stream<T>, B> batchJob,
                              Function<Stream<B>, R> reduceFunction) throws InterruptedException {
        List<List<T>> tasksDistributions = makeDistributionForThreads(threads, list);
        threads = tasksDistributions.size();
        List<B> tasksResults = new ArrayList<>(Collections.nCopies(threads, null));
        List<Thread> workers = new ArrayList<>();

        for (int index = 0; index < threads; index++) {
            final int finalIndex = index;
            Thread thread = new Thread(
                    () -> tasksResults.set(finalIndex, batchJob.apply(tasksDistributions.get(finalIndex).stream())));
            workers.add(thread);
            thread.start();
        }

        for (int i = 0; i < threads; i++) {
            try {
                workers.get(i).join();
            } catch (InterruptedException e) {
                InterruptedException exception = new InterruptedException("Some threads were interrupted");
                exception.addSuppressed(e);
                for (int j = i; j < threads; j++) {
                    workers.get(j).interrupt();
                }
                for (int j = i; j < threads; j++) {
                    try {
                        workers.get(j).join();
                    } catch (InterruptedException e1) {
                        e.addSuppressed(e1);
                        --j;
                    }
                }
                throw exception;
            }
        }
        return reduceFunction.apply(tasksResults.stream());
    }

    @Override
    public <T> T maximum(int threads, List<? extends T> values, Comparator<? super T> comparator)
            throws InterruptedException {
        return runIP(threads, values,
                stream -> stream.max(comparator).orElseThrow(),
                stream -> stream.max(comparator).orElseThrow());
    }

    @Override
    public <T> T minimum(int threads, List<? extends T> values, Comparator<? super T> comparator)
            throws InterruptedException {
        return maximum(threads, values, comparator.reversed());
    }

    @Override
    public <T> boolean all(int threads, List<? extends T> values, Predicate<? super T> predicate)
            throws InterruptedException {
        return runIP(threads, values,
                stream -> stream.allMatch(predicate),
                stream -> stream.allMatch(Boolean::booleanValue));
    }

    @Override
    public <T> boolean any(int threads, List<? extends T> values, Predicate<? super T> predicate)
            throws InterruptedException {
        return !all(threads, values, predicate.negate());
    }
}
