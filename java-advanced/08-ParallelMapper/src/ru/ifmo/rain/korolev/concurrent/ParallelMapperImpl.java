package ru.ifmo.rain.korolev.concurrent;

import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.*;
import java.util.function.Function;


/**
 * Implementation of {@link ParallelMapper} interface using parallelism for apply function.
 *
 * @author Korolev Daniil
 * @version 239.0
 */
public class ParallelMapperImpl implements ParallelMapper {
    private final Queue<Runnable> tasks;
    private final List<Thread> workers;

    /**
     * @param threads number of {@link Thread}s to use.
     * @throws InterruptedException throws in case some thread interrupt.
     */
    public ParallelMapperImpl(final int threads) throws InterruptedException {
        if (threads <= 0) {
            throw new IllegalArgumentException("Number of threads must be positive");
        }

        tasks = new ArrayDeque<>();
        workers = new ArrayList<>();
        InterruptedException exceptions = new InterruptedException();
        for (int i = 0; i < threads; i++) {
            Thread thread = new Thread(
                    () -> {
                        try {
                            while (!Thread.interrupted()) {
                                Runnable task;
                                synchronized (tasks) {
                                    while (tasks.isEmpty()) {
                                        tasks.wait();
                                    }
                                    task = tasks.poll();
                                }
                                task.run();
                            }
                        } catch (InterruptedException e) {
                            exceptions.addSuppressed(e);
                        } finally {
                            Thread.currentThread().interrupt();
                        }
                    });
            workers.add(thread);
            thread.start();
        }
        if (exceptions.getSuppressed().length != 0) {
            throw exceptions;
        }
    }

    private void createTask(final Runnable task) {
        synchronized (tasks) {
            tasks.add(task);
            tasks.notify();
        }
    }

    private static class TasksStorage<T> {
        private List<T> data;
        private int doneTasks = 0;

        private TasksStorage(final int size) {
            this.data = new ArrayList<>(Collections.nCopies(size, null));
        }

        void set(final T value, final int index) {
            data.set(index, value);
            synchronized (this) {
                ++doneTasks;
                if (doneTasks == data.size()) {
                    notify();
                }
            }
        }

        synchronized List<T> getElements() throws InterruptedException {
            while (doneTasks < data.size()) {
                wait();
            }
            return data;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T, R> List<R> map(final Function<? super T, ? extends R> function,
                              final List<? extends T> values) throws InterruptedException {
        TasksStorage<R> storage = new TasksStorage<>(values.size());
        for (int i = 0; i < values.size(); i++) {
            final int taskIndex = i;
            createTask(() -> storage.set(function.apply(values.get(taskIndex)), taskIndex));
        }
        return storage.getElements();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        workers.forEach(Thread::interrupt);
    }
}
