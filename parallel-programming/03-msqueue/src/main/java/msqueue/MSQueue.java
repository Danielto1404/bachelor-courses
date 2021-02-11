package msqueue;

import kotlinx.atomicfu.AtomicRef;

public class MSQueue implements Queue {

    private AtomicRef<Node> head;
    private AtomicRef<Node> tail;


    private static class Node {
        final AtomicRef<Node> next;
        final int x;

        Node(int x) {
            this.next = new AtomicRef<>(null);
            this.x = x;
        }
    }


    public MSQueue() {
        Node dummy = new Node(0);
        this.head = new AtomicRef<>(dummy);
        this.tail = new AtomicRef<>(dummy);
    }


    @Override
    public void enqueue(int x) {

        Node last = new Node(x);

        while (true) {

            Node currentTail = this.tail.getValue();
            AtomicRef<Node> nextAfterTail = currentTail.next;

            if (nextAfterTail.compareAndSet(null, last)) {
                this.tail.compareAndSet(currentTail, last);
                return;
            }

            this.tail.compareAndSet(currentTail, currentTail.next.getValue());
        }
    }

    @Override
    public int dequeue() {

        while (true) {
            Node dummyFirst = head.getValue();
            Node first = dummyFirst.next.getValue();

            if (first == null) {
                return Integer.MIN_VALUE;
            }

            if (head.compareAndSet(dummyFirst, first)) {
                return first.x;
            }
        }
    }

    @Override
    public int peek() {
        // Point of linearization is head.getValue()
        // At this point we read our current dummy top
        Node top = head.getValue().next.getValue();
        return top == null ? Integer.MIN_VALUE : top.x;
    }
}