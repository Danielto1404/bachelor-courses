import kotlinx.atomicfu.*
import java.lang.IllegalArgumentException

class DynamicArrayImpl<E> : DynamicArray<E> {

    private val emptyBlock = {}
    private val core = atomic(Core<E>(INITIAL_CAPACITY))

    override val size: Int
        get() {
            return currentState.size.value
        }

    override fun get(index: Int): E {
        while (true) {
            val state = currentState
            state.checkIndex(index)
            val node = state.array[index].value!!
            val value = node.value
            return when (node is Fixed) {
                true -> casNext(state, index, value) { value } ?: continue
                else -> value
            }
        }
    }

    override fun put(index: Int, element: E) {
        while (true) {
            val state = currentState
            state.checkIndex(index)
            val new = Node(element)
            val old = state.array[index].value
            return when (old is Fixed) {
                true -> casNext(state, index, element, emptyBlock) ?: continue
                else -> when (state.array[index].compareAndSet(old, new)) {
                    true -> return
                    else -> continue
                }
            }
        }
    }

    override fun pushBack(element: E) {
        while (true) {
            val state = currentState
            val size = state.size.value
            val new = Node(element)
            if (size >= state.capacity) {
                if (!ensureCapacity(state)) {
                    continue
                }
            } else if (state.array[size].compareAndSet(null, new)) {
                state.incrementSize(size)
                return
            }
        }
    }

    private val currentState: Core<E>
        get() {
            return core.value
        }

    private fun ensureCapacity(state: Core<E>): Boolean {
        if (state.next.compareAndSet(null, Core(2 * state.capacity))) {
            for (i in 0 until state.capacity) {
                while (true) {
                    val old = state.array[i].value
                    val value = old!!.value
                    val fixed = Fixed(value)
                    if (state.array[i].compareAndSet(old, fixed)) {
                        casNext(state, i, value, emptyBlock)
                        break
                    }
                }
            }
            core.compareAndSet(state, state.next.value!!)
            return true
        }
        return false
    }

    private fun <Result> casNext(state: Core<E>, index: Int, value: E, completionBlock: () -> Result): Result? {
        if (state.next.value!!.array[index].compareAndSet(null, Node(value))) {
            return completionBlock()
        }
        return null
    }
}

open class Node<E>(val value: E)
private class Fixed<E>(fixedValue: E) : Node<E>(fixedValue)


private class Core<E>(val capacity: Int) {

    val array: AtomicArray<Node<E>?> = atomicArrayOfNulls(capacity)
    val size: AtomicInt = atomic(capacity / 2)
    val next: AtomicRef<Core<E>?> = atomic(null)

    fun checkIndex(index: Int) {
        if (index >= this.size.value) {
            throw IllegalArgumentException()
        }
    }

    fun incrementSize(currentSize: Int) {
        when (size.compareAndSet(currentSize, currentSize + 1)) {
            true -> return
            else -> incrementSize(currentSize)
        }
    }
}

private const val INITIAL_CAPACITY = 1 // DO NOT CHANGE ME