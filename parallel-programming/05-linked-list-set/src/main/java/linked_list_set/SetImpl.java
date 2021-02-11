package linked_list_set;

import kotlinx.atomicfu.AtomicRef;

public class SetImpl implements Set {

    private final AtomicRef<Node> head = new AtomicRef<>(
            new Node(Integer.MIN_VALUE,
                    new Node(Integer.MAX_VALUE,
                            null)
            )
    );

    /**
     * Returns the {@link Window}, where prev.x < x <= cur.x
     */
    private Window findWindow(int x) {

        Window w = new Window();

        retry:
        while (true) {

            w.current = head.getValue();
            w.next = w.current.getNextValue();

            while (w.next.getKey() < x) {

                INode next = w.next.getNextValue();

                if (next.isRemoved()) {
                    RemovedNode newNext = (RemovedNode) next;
                    if (!w.current.next().compareAndSet(w.next, newNext.node)) {
                        continue retry;
                    }
                    w.next = newNext.node;
                    continue;
                }

                w.current = w.next;
                w.next = next;
            }

            return w;
        }
    }

    public boolean add(int x) {
        while (true) {
            Window w = findWindow(x);

            if (w.next.getKey() == x && !w.next.getNextValue().isRemoved()) {
                return false;
            }

            INode newNode = new Node(x, w.next);
            if (w.current.next().compareAndSet(w.next, newNode)) {
                return true;
            }
        }
    }

    public boolean remove(int x) {
        while (true) {
            Window w = findWindow(x);

            if (w.next.getKey() != x) {
                return false;
            }

            INode newNext = w.next.next().getValue();

            if (w.next.next().getValue().isRemoved()) {
                return false;
            }
            // Here we make logical deletion by converting Node object to RemovedNode.
            if (w.next.next().compareAndSet(newNext, new RemovedNode((Node) newNext))) {
                w.current.next().compareAndSet(w.next, newNext);
                return true;
            }
        }
    }

    public boolean contains(int x) {
        INode node = findWindow(x).next;
        return node.getKey() == x && !node.next().getValue().isRemoved();
    }

    /**
     * Interface representing basic linked list operations
     */
    private interface INode {
        int getKey();

        AtomicRef<INode> next();

        INode getNextValue();

        /**
         * Stores removed flag to previous node.
         *
         * @return Boolean value indicating whether previous node is removed.
         */
        boolean isRemoved();
    }

    /**
     * Window class representing
     */
    private static class Window {
        private INode current, next;
    }

    /**
     * Node representing existing INode objects in linked list.
     */
    private static class Node implements INode {
        private final int x;
        private final AtomicRef<INode> next;

        Node(int x, INode next) {
            this.x = x;
            this.next = new AtomicRef<>(next);
        }

        @Override
        public int getKey() {
            return x;
        }

        @Override
        public AtomicRef<INode> next() {
            return next;
        }

        @Override
        public INode getNextValue() {
            return next.getValue();
        }

        @Override
        public boolean isRemoved() {
            return false;
        }
    }

    /**
     * Removed represent logical deleted INode objects in linked list.
     */
    private static class RemovedNode implements INode {
        private final Node node;

        RemovedNode(Node node) {
            this.node = node;
        }

        @Override
        public int getKey() {
            return node.x;
        }

        @Override
        public AtomicRef<INode> next() {
            return node.next;
        }

        @Override
        public INode getNextValue() {
            return node.next.getValue();
        }

        @Override
        public boolean isRemoved() {
            return true;
        }
    }
}