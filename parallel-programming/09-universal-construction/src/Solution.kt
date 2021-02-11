/**
 * @author : Korolev Daniil
 */
class Solution : AtomicCounter {

    private val root: Node = Node(0)
    private val last: ThreadLocal<Node> = ThreadLocal()

    override fun getAndAdd(x: Int): Int {

        if (last.get() == null) {
            last.set(root)
        }

        while (true) {
            val old = last.get().value
            val actual = old + x
            val node = Node(actual)
            val threadLocalLast = last.get().next.decide(node)
            last.set(threadLocalLast)
            if (threadLocalLast == node) {
                return old
            }
        }
    }

    /**
     * Node class which represents current value and
     * consensus field to decide which operation we should perform in concurrent case.
     */
    internal class Node(val value: Int, val next: Consensus<Node> = Consensus())
}