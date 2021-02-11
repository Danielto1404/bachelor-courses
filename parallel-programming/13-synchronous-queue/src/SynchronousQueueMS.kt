import kotlinx.atomicfu.AtomicRef
import kotlinx.atomicfu.atomic
import java.lang.IllegalStateException
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

const val RETRY = "RETRY"

class SynchronousQueueMS<E> : SynchronousQueue<E> {

    private inner class Node(type: OperationType? = null, element: E? = null) {
        val continuation: AtomicRef<Continuation<Any?>?> = atomic(null)
        val type: AtomicRef<OperationType?> = atomic(type)
        val next: AtomicRef<Node?> = atomic(null)
        val element: AtomicRef<E?> = atomic(element)

        fun isReceiver() = type.value == OperationType.RECEIVER
        fun isSender() = type.value == OperationType.SENDER
    }

    private enum class OperationType {
        SENDER, RECEIVER
    }

    private enum class ResultType {
        UNIT, VALUE
    }

    private val dummy = Node()
    private val head = atomic(dummy)
    private val tail = atomic(dummy)

    private suspend fun enqueueAndSuspend(tail: Node, node: Node): Boolean {
        val result = suspendCoroutine<Any?> action@{ process ->
            node.continuation.value = process
            if (!tail.next.compareAndSet(null, node)) {
                this.tail.compareAndSet(tail, tail.next.value!!)
                process.resume(RETRY)
                return@action
            } else {
                this.tail.compareAndSet(tail, node)
            }
        }
        return result != RETRY
    }


    private fun dequeueAndResume(head: Node, element: E?): Boolean {
        val headNext = head.next.value ?: return false

        return if (this.head.compareAndSet(head, headNext)) {
            if (element != null) {
                headNext.element.value = element
            }
            headNext.continuation.value?.resume(null)
            true
        } else {
            false
        }
    }

    override suspend fun send(element: E) =
        communicate(element, ResultType.UNIT, OperationType.SENDER) { node -> node.isSender() } as Unit

    override suspend fun receive() =
        communicate(null, ResultType.VALUE, OperationType.RECEIVER) { node -> node.isReceiver() } as E


    private suspend fun communicate(
        element: E?, resultType: ResultType, operationType: OperationType, checkType: (Node) -> Boolean
    ): Any {
        while (true) {
            val head = head.value
            val tail = tail.value

            val node = Node(operationType, element)

            if (tail == head || checkType(tail)) {
                if (enqueueAndSuspend(tail, node)) {
                    return when (resultType) {
                        ResultType.UNIT -> Unit
                        ResultType.VALUE -> node.element.value as Any
                    }
                }
            } else {
                if (tail == this.tail.value) {
                    if (dequeueAndResume(head, element)) {
                        val headNext = head.next.value ?: throw IllegalStateException()
                        return when (resultType) {
                            ResultType.UNIT -> Unit
                            ResultType.VALUE -> headNext.element.value as Any
                        }
                    }
                }
            }
        }
    }
}
