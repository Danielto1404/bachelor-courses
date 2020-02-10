package queue;

public class ArrayQueueModule {
    private static int capacity = 4;
    private static Object[] elements = new Object[capacity];
    private static int curSize = 0;
    private static int head = 0;
    private static int tail = 0;

    // PRE: x != null ∧ tail in [0..capacity) ∧ ∀i : elements[i] != null
    // POST: size' = size + 1 ∧ elements[size' - 1] = x ∧ tail' = (tail + 1) % capacity ∧ ∀i=1..n : a[i]' = a[i]
    public static void enqueue(Object x) {
        if (curSize == 0) {
            elements[head] = x;
            tail = head;
        } else {
            if (curSize >= capacity) {
                ensureCapacity();
            }
            tail = next(tail);
            // Cycle shift of tail
            elements[tail] = x;
        }
        curSize++;
    }


    // PRE: head in [0..curSize) ∧ ∀i : elements[i] != null
    // POST: ℝ = elements[head] ∧ head' = head
    public static Object dequeue() {
        if (!isEmpty()) {
            Object result = elements[head];
            if (curSize == 1) {
                head = 0;
                tail = head;
                curSize = head;
            } else {
                head = next(head);
                curSize--;
            }
            return result;
        }
        throw new RuntimeException("< queue is empty >");
    }

    // PRE: curSize >= 0
    // POST: ℝ = curSize == 0 ∧ ∀i = 1..n : a[i]' = a[i]
    public static boolean isEmpty() {
        assert curSize >= 0;
        return curSize == 0;
    }

    // PRE: size >= 0
    // POST: ℝ = curSize == 0 ∧ n = n' ∧ ∀i = 1..curSize : a[i]' = a[i]
    public static int size() {
        assert curSize >= 0;
        return curSize;
    }

    // PRE: curSize >= 0 ∧ ∀i : elements[i] != null
    // POST: for i in [0..curSize] : elements[i] == null
    public static void clear() {
        assert curSize >= 0;
        elements = new Object[1];
        curSize = 0;
    }

    // PRE: curSize >= 0 ∧ ∀i : elements[i] != null
    // POST: elements.length() == capacity ∧ head == 0 ∧ tail = curSize - 1 ∧  ∀i : elements[i] = elements[i]'
    private static void ensureCapacity() {
        assert curSize >= 0;
        Object[] copyArray = new Object[2 * capacity];
        if (curSize < capacity) {
            return;
        } else {
            if (tail <= head) {
                // PRE: ∀i : a[i] != null ∧ head >= tail
                // POST: ∀i in [head..capacity - 1] => copyArray[i - head] = elements[i]
                // We made  shift from [head] to [0] => our pointer is on [capacity - head - 1]
                // ∀i in [0..tail] => copyArray[capacity - head + i] = elements[i]
                System.arraycopy(elements, head, copyArray, 0, capacity - head);
                System.arraycopy(elements, 0, copyArray, capacity - head, tail + 1);
            } else {
                // PRE: ∀i : a[i] != null ∧ head < tail
                // POST: We copy array from [head..tail]
                // head < tail ∧ head < tail => tail = curSize - 1 ∧ head = 0
                System.arraycopy(elements, 0, copyArray, 0, capacity);
            }
        }
        capacity *= 2;
        elements = copyArray;
        head = 0;
        tail = curSize - 1;
    }

    // PRE: head in [0..capacity) ∧ ∀i : elements[i] != null
    // POST: ℝ = elements[head] ∧ ∀i = 1..n : a[i]' = a[i]
    public static Object element() {
        assert curSize > 0;
        return elements[head];
    }

    // PRE: i < capacity ∧ i >= 0
    // POST: ℝ = (i + 1) % capacity ∧ ∀i : next(i) in [0..capacity)
    private static int next(int i) {
        return (i + 1) % capacity;
    }

    // PRE:  i < capacity ∧ i >= 0
    // POST: ℝ = (capacity + i - 1) % capacity ∧ ∀i : next(i) in [0..capacity)
    private static int prev(int i) {
        return (capacity + i - 1) % capacity;
    }

    // PRE: ∀i : a[i] != null ∧ tail in [0..capacity) ∧ curSize > 0
    // POST: ℝ = elements[tail] ∧ curSize > 0
    public static Object peek() {
        assert curSize > 0;
        return elements[tail];
    }

    // PRE: curSize >= 0 ∧ ∀i : a[i] != null
    // POST: head' = prev(head) ∧ ∀i : a[i] = a[i]' ∧ elements[head'] = element
    public static void push(Object element) {
        assert curSize >= 0;
        if (curSize == 0) {
            elements[curSize] = element;
            head = tail;
        } else {
            if (curSize == capacity) {
                ensureCapacity();
            }
            head = prev(head);
            elements[head] = element;
        }
        curSize++;
    }

    // PRE: curSize > 0 ∧ ∀i : a[i] != null ∧ tail in [0..capacity)
    // POST: tail' = prev(tail) ∧ curSize' = curSize - 1 ∧ tail in [0..capacity)
    // ℝ = elements[tail] (last element)
    public static Object remove() {
        assert curSize > 0;
        if (!isEmpty()) {
            Object result = elements[tail];
            if (curSize == 1) {
                head = 0;
                tail = head;
            } else {
                tail = prev(tail);
            }
            curSize--;
            return result;
        }
        throw new RuntimeException("< queue is empty >");
    }
}