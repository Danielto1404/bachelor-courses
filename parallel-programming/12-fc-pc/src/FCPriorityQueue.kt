import kotlinx.atomicfu.AtomicBoolean
import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.atomicArrayOfNulls
import java.util.*
import kotlin.math.max
import kotlin.random.Random

class FCPriorityQueue<E : Comparable<E>> {

    // Operations in flat combining array
    open class PQOperation<E>(val value: E? = null)
    private class Peek<E> : PQOperation<E>()
    private class Poll<E> : PQOperation<E>()
    private class Add<E>(value: E) : PQOperation<E>(value)
    private class Done<E>(value: E? = null) : PQOperation<E>(value)

    private val size = max(Runtime.getRuntime().availableProcessors(), 4) * 32
    private val operations = atomicArrayOfNulls<PQOperation<E>>(size)

    private val q = PriorityQueue<E>()

    // Lock via CAS
    private val isLocked: AtomicBoolean = atomic(false)


    private fun lock(): Boolean {
        return isLocked.compareAndSet(expect = false, update = true)
    }

    private fun unlock() {
        isLocked.value = false
    }

    private val randomIndex: Int
        get() = Random.nextInt(size)

    private fun insertOperationAndGetIndex(operation: PQOperation<E>): Int {
        while (true) {
            val index = randomIndex
            if (operations[index].compareAndSet(null, operation)) {
                return index
            }
        }
    }

    private fun applyOperations() {
        for (i in 0 until size) {
            val result = when (val operation = operations[i].value) {
                is Poll -> Done(q.poll())
                is Peek -> Done(q.peek())
                is Add -> {
                    q.add(operation.value)
                    Done()
                }
                else -> continue
            }
            operations[i].value = result
        }
    }

    private fun <Result> processOperation(operationType: PQOperation<E>, queueOperation: () -> Result): Result {
        if (lock()) {
            val result = queueOperation()
            applyOperations()
            unlock()
            return result
        } else {
            val index = insertOperationAndGetIndex(operationType)
            while (true) {
                if (lock()) {
                    val operationStatus = operations[index].value
                    operations[index].value = null
                    val result = if (operationStatus is Done) {
                        operationStatus.value as Result
                    } else {
                        queueOperation()
                    }
                    applyOperations()
                    unlock()
                    return result
                }
                val operationStatus = operations[index].value
                if (operationStatus is Done) {
                    operations[index].value = null
                    return operationStatus.value as Result
                }
            }
        }
    }

    /**
     * Retrieves the element with the highest priority
     * and returns it as the result of this function;
     * returns `null` if the queue is empty.
     */
    fun poll(): E? {
        return processOperation(Poll()) { q.poll() }
    }

    /**
     * Returns the element with the highest priority
     * or `null` if the queue is empty.
     */
    fun peek(): E? {
        return processOperation(Peek()) { q.peek() }
    }

    /**
     * Adds the specified element to the queue.
     */
    fun add(element: E) {
        processOperation(Add(element)) { q.add(element) }
    }
}
