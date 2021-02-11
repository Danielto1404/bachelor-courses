import kotlinx.atomicfu.*

class FAAQueue<T> {
    private val head: AtomicRef<Segment> // Head pointer, similarly to the Michael-Scott queue (but the first node is _not_ sentinel)
    private val tail: AtomicRef<Segment> // Tail pointer, similarly to the Michael-Scott queue

    init {
        val initialNode = Segment()
        head = atomic(initialNode)
        tail = atomic(initialNode)
    }

    /**
     * Adds the specified element [x] to the queue.
     */
    fun enqueue(x: T) {
        while (true) {
            val tail = shiftTail()
            val enqIdx = tail.enqIdx.getAndIncrement()
            if (enqIdx >= SEGMENT_SIZE) {
                val newTail = Segment(x)
                if (this.tail.value.next.compareAndSet(null, newTail)) {
                    return
                }
            } else if (tail.elements[enqIdx].compareAndSet(null, x)) {
                return
            }
        }
    }

    /**
     * Retrieves the first element from the queue
     * and returns it; returns `null` if the queue
     * is empty.
     */
    fun dequeue(): T? {
        while (true) {
            val head = head.value
            if (head.isEmpty) {
                val headNext = head.next.value ?: return null
                this.head.compareAndSet(head, headNext)
            } else {
                val deqIdx = head.deqIdx.getAndIncrement()
                if (deqIdx >= SEGMENT_SIZE) {
                    continue
                }
                val e = head.elements[deqIdx].getAndSet(DONE) ?: continue
                return e as? T
            }
        }
    }

    /**
     * Returns `true` if this queue is empty;
     * `false` otherwise.
     */
    val isEmpty: Boolean
        get() {
            while (true) {
                val head = head.value
                if (head.isEmpty) {
                    val headNext = head.next.value ?: return true
                    this.head.compareAndSet(head, headNext)
                } else {
                    return false
                }
            }
        }

    private fun shiftTail(): Segment {
        val tail = tail.value
        val tailNext = tail.next.value ?: return tail
        this.tail.compareAndSet(tail, tailNext)
        return shiftTail()
    }
}

private class Segment {
    val next: AtomicRef<Segment?> = atomic(null)
    val enqIdx: AtomicInt = atomic(0) // index for the next enqueue operation
    val deqIdx: AtomicInt = atomic(0) // index for the next dequeue operation
    val elements: AtomicArray<Any?> = atomicArrayOfNulls(SEGMENT_SIZE)

    constructor() // for the first segment creation

    constructor(x: Any?) { // each next new segment should be constructed with an element
        enqIdx.value = 1
        elements[0].value = x
    }

    val isEmpty: Boolean
        get() = deqIdx.value >= SEGMENT_SIZE
}

private val DONE = Any() // Marker for the "DONE" slot state; to avoid memory leaks
private const val SEGMENT_SIZE = 2 // DO NOT CHANGE, IMPORTANT FOR TESTS

