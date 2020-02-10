package queue;

public class ArrayQueueADT {
    private int DEFAULT_SIZE = 4;
    private Object[] elements = new Object[DEFAULT_SIZE];
    private int curSize = 0;
    private int head = 0;
    private int tail = 0;
    private int capacity = DEFAULT_SIZE;

    // PRE: x != null ∧ tail in [0..capacity) ∧ ∀i : elements[i] != null
    // POST: size' = size + 1 ∧ elements[size' - 1] = x ∧ tail' = (tail + 1) % capacity ∧ ∀i=1..n : a[i]' = a[i]
    public static void enqueue(ArrayQueueADT arrayQueueADT, Object x) {
        if (arrayQueueADT.curSize == 0) {
            arrayQueueADT.elements[arrayQueueADT.head] = x;
            arrayQueueADT.tail = arrayQueueADT.head;
        } else {
            if (arrayQueueADT.curSize >= arrayQueueADT.capacity) {
                ensureCapacity(arrayQueueADT);
            }
            arrayQueueADT.tail = next(arrayQueueADT.tail, arrayQueueADT);
            // Cycle shift of tail
            arrayQueueADT.elements[arrayQueueADT.tail] = x;
        }
        arrayQueueADT.curSize++;
    }

    // PRE: head in [0..curSize) ∧ ∀i : elements[i] != null
    // POST: ℝ = elements[head] ∧ head' = head
    public static Object dequeue(ArrayQueueADT arrayQueueADT) {
        assert arrayQueueADT.curSize > 0 : "< queue is empty >";
        Object result = arrayQueueADT.elements[arrayQueueADT.head];
        if (arrayQueueADT.curSize == 1) {
            arrayQueueADT.head = 0;
            arrayQueueADT.tail = arrayQueueADT.head;
            arrayQueueADT.curSize = arrayQueueADT.head;
        } else {
            arrayQueueADT.head = next(arrayQueueADT.head, arrayQueueADT);
            arrayQueueADT.curSize--;
        }
        return result;
    }

    // PRE: curSize >= 0
    // POST: ℝ = curSize == 0 ∧ ∀i = 1..n : a[i]' = a[i]
    public static boolean isEmpty(ArrayQueueADT arrayQueueADT) {
        assert arrayQueueADT.curSize >= 0;
        return arrayQueueADT.curSize == 0;
    }

    // PRE: size >= 0
    // POST: ℝ = curSize == 0 ∧ n = n' ∧ ∀i = 1..curSize : a[i]' = a[i]
    public static int size(ArrayQueueADT arrayQueueADT) {
        assert arrayQueueADT.curSize >= 0;
        return arrayQueueADT.curSize;
    }

    // PRE: curSize >= 0 ∧ ∀i : elements[i] != null
    // POST: for i in [0..curSize] : elements[i] == null
    public static void clear(ArrayQueueADT arrayQueueADT) {
        assert arrayQueueADT.curSize >= 0;
        arrayQueueADT.elements = new Object[arrayQueueADT.DEFAULT_SIZE];
        arrayQueueADT.curSize = 0;
    }

    // PRE: curSize >= 0 ∧ ∀i : elements[i] != null
    // POST: elements.length() == capacity ∧ head == 0 ∧ tail = curSize - 1 ∧  ∀i : elements[i] = elements[i]'
    private static void ensureCapacity(ArrayQueueADT arrayQueueADT) {
        assert arrayQueueADT.curSize >= 0;
        Object[] copyArray = new Object[2 * arrayQueueADT.capacity];
        if (arrayQueueADT.curSize < arrayQueueADT.capacity) {
            return;
        } else {
            if (arrayQueueADT.tail <= arrayQueueADT.head) {
                // PRE: ∀i : a[i] != null ∧ head >= tail
                // POST: ∀i in [head..capacity - 1] => copyArray[i - head] = elements[i]
                // We made  shift from [head] to [0] => our pointer is on [capacity - head - 1]
                // ∀i in [0..tail] => copyArray[capacity - head + i] = elements[i]
                System.arraycopy(arrayQueueADT.elements, arrayQueueADT.head, copyArray, 0,
                        arrayQueueADT.capacity - arrayQueueADT.head);
                System.arraycopy(arrayQueueADT.elements, 0, copyArray,
                        arrayQueueADT.capacity - arrayQueueADT.head, arrayQueueADT.tail + 1);
            } else {
                // PRE: ∀i : a[i] != null ∧ head < tail
                // POST: We copy array from [head..tail]
                // head < tail ∧ head < tail => tail = curSize - 1 ∧ head = 0
                System.arraycopy(arrayQueueADT.elements, 0, copyArray, 0, arrayQueueADT.capacity);
            }
        }
        arrayQueueADT.capacity *= 2;
        arrayQueueADT.elements = copyArray;
        arrayQueueADT.head = 0;
        arrayQueueADT.tail = arrayQueueADT.curSize - 1;
    }

    // PRE: head in [0..capacity) ∧ ∀i : elements[i] != null
    // POST: ℝ = elements[head] ∧ ∀i = 1..n : a[i]' = a[i]
    public static Object element(ArrayQueueADT arrayQueueADT) {
        assert arrayQueueADT.curSize > 0 : "< queue is empty >";
        return arrayQueueADT.elements[arrayQueueADT.head];
    }

    // PRE: i < capacity ∧ i >= 0
    // POST: ℝ = (i + 1) % capacity ∧ ∀i : next(i) in [0..capacity)
    private static int next(int i, ArrayQueueADT arrayQueueADT) {
        return (i + 1) % arrayQueueADT.capacity;
    }

    // PRE:  i < capacity ∧ i >= 0
    // POST: ℝ = (capacity + i - 1) % capacity ∧ ∀i : next(i) in [0..capacity)
    private static int prev(int i, ArrayQueueADT arrayQueueADT) {
        return (arrayQueueADT.capacity + i - 1) % arrayQueueADT.capacity;
    }

    // PRE: ∀i : a[i] != null ∧ tail in [0..capacity) ∧ curSize > 0
    // POST: ℝ = elements[tail] ∧ curSize > 0
    public static Object peek(ArrayQueueADT arrayQueueADT) {
        assert arrayQueueADT.curSize > 0 : "< queue is empty >";
        return arrayQueueADT.elements[arrayQueueADT.tail];
    }

    // PRE: curSize >= 0 ∧ ∀i : a[i] != null
    // POST: head' = prev(head) ∧ ∀i : a[i] = a[i]' ∧ elements[head'] = element
    public static void push(Object element, ArrayQueueADT arrayQueueADT) {
        assert arrayQueueADT.curSize >= 0;
        if (arrayQueueADT.curSize == 0) {
            arrayQueueADT.elements[arrayQueueADT.curSize] = element;
            arrayQueueADT.head = arrayQueueADT.tail;
        } else {
            if (arrayQueueADT.curSize == arrayQueueADT.capacity) {
                ensureCapacity(arrayQueueADT);
            }
            arrayQueueADT.head = prev(arrayQueueADT.head, arrayQueueADT);
            arrayQueueADT.elements[arrayQueueADT.head] = element;
        }
        arrayQueueADT.curSize++;
    }

    // PRE: curSize > 0 ∧ ∀i : a[i] != null ∧ tail in [0..capacity)
    // POST: tail' = prev(tail) ∧ curSize' = curSize - 1 ∧ tail in [0..capacity)
    // ℝ = elements[tail] (last element)
    public static Object remove(ArrayQueueADT arrayQueueADT) {
        assert arrayQueueADT.curSize > 0 : "< queue is empty >";
        Object result = arrayQueueADT.elements[arrayQueueADT.tail];
        if (arrayQueueADT.curSize == 1) {
            arrayQueueADT.head = 0;
            arrayQueueADT.tail = arrayQueueADT.head;
        } else {
            arrayQueueADT.tail = prev(arrayQueueADT.tail, arrayQueueADT);
        }
        arrayQueueADT.curSize--;
        return result;
    }
}

