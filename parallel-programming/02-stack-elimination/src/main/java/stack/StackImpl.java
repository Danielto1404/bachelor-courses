package stack;

import kotlinx.atomicfu.AtomicArray;
import kotlinx.atomicfu.AtomicRef;

import java.util.Random;

public class StackImpl implements Stack {

    // Constant which varies to improve performance
    private static final int eliminationArraySize = 128;
    private static final int maxSearchIterationsCount = 16;

    // Random number generator
    private static final Random randomGenerator = new Random();


    private AtomicArray<Integer> eliminationArray = new AtomicArray<>(eliminationArraySize);
    private AtomicRef<Node> head = new AtomicRef<>(null);

    private static class Node {
        final AtomicRef<Node> next;
        final int x;

        Node(int x, Node next) {
            this.next = new AtomicRef<>(next);
            this.x = x;
        }
    }


    private int getStartIndex() {
        // This bound describes sequentially cells in memory while reading element in elimination array
        return randomGenerator.nextInt(eliminationArraySize - maxSearchIterationsCount);
    }


    @Override
    public void push(int x) {

        int startIndex = getStartIndex();
        Integer intReference = x;

        for (int offset = 0; offset < maxSearchIterationsCount; ++offset) {

            AtomicRef<Integer> wrapped = eliminationArray.get(startIndex + offset);

            if (wrapped.compareAndSet(null, intReference)) {

                for (int searchIteration = 0; searchIteration < maxSearchIterationsCount; ++searchIteration) {
                    Integer value = wrapped.getValue();
                    if (value == null || value != x) {
                        return;
                    }
                }

                if (wrapped.compareAndSet(intReference, null)) {
                    break;
                }

                // Element was eliminated by pop operation
                return;
            }
        }

        // Basic push lock free algorithm
        while (true) {
            Node curHead = head.getValue();
            Node newHead = new Node(x, curHead);
            if (head.compareAndSet(curHead, newHead)) {
                return;
            }
        }
    }


    @Override
    public int pop() {

        int startIndex = getStartIndex();

        for (int offset = 0; offset < maxSearchIterationsCount; ++offset) {

            AtomicRef<Integer> wrapped = eliminationArray.get(startIndex + offset);
            Integer value = wrapped.getValue();
            if (value != null && wrapped.compareAndSet(value, null)) {
                return value;
            }
        }

        // Basic pop lock free algorithm
        while (true) {
            Node curHead = head.getValue();
            if (curHead == null) {
                return Integer.MIN_VALUE;
            }
            if (head.compareAndSet(curHead, curHead.next.getValue())) {
                return curHead.x;
            }
        }
    }
}
