import java.util.concurrent.atomic.*

class Solution(private val environment: Environment) : Lock<Solution.Node> {
    private val tail = AtomicReference<Node?>(null)

    override fun lock(): Node {
        val node = Node(true)
        val next = tail.getAndSet(node)?.next ?: return node
        next.value = node
        while (node.locked.value) {
            environment.park()
        }
        return node
    }

    override fun unlock(node: Node) {
        if (node.next.value == null) {
            if (tail.compareAndSet(node, null)) {
                return
            }
            while (node.next.value == null) {
                continue
            }
        }
        val next = node.next.value ?: return
        next.locked.value = false
        environment.unpark(next.thread)
    }

    class Node(isLocked: Boolean) {
        val locked: AtomicReference<Boolean> = AtomicReference(isLocked)
        val next: AtomicReference<Node?> = AtomicReference(null)
        val thread: Thread = Thread.currentThread()
    }
}