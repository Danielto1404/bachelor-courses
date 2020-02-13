package queue;

public class LinkedQueue extends AbstractQueue {

    private class Node {
        Node next;
        Object value;

        // PRE: value != null
        Node(Object value) {
            assert value != null;
            this.value = value;
            this.next = null;
        }
    }

    private Node head;
    private Node tail;

    // PRE: element != null
    protected void enqueueImpl(Object element) {
        if (size == 0) {
            head = new Node(element);
            tail = head;
        } else {
            tail.next = new Node(element);
            tail = tail.next;
        }
        tail.next = null;
    }


    // PRE: ∀ node in queue : node.value != null ∧ size > 0
    // POST: ℝ = head.value ∧ head' = head.next ∧ size' = size - 1
    protected void dequeueImpl() {
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            head = head.next;
        }
    }

    // POST: head = null ∧ tail = null ∧ size = 0
    protected void clearImpl() {
        head = null;
        tail = null;
    }

    // PRE: element != null
    // POST: head'.next = head ∧ head'.value = element
    protected void pushImpl(Object element) {
        if (size == 0) {
            enqueueImpl(element);
        } else {
            Node tmp = new Node(element);
            tmp.next = head;
            head = tmp;
        }
    }

    protected Object peekImpl() {
        return tail.value;
    }

    protected Object removeImpl() {
        Object res = peekImpl();
        Node pointer = head;
        if (size == 1) {
            head = null;
            pointer = null;
        } else {
            while (pointer.next != tail && pointer.next != null) {
                pointer = pointer.next;
            }
        }
        tail = pointer;
        return res;
    }

    protected Object elementImpl() {
        return head.value;
    }

    protected Queue typeQueue() {
        return new LinkedQueue();
    }
}
