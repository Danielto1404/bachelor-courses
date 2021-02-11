package dijkstra

import java.util.*
import java.util.concurrent.Phaser
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.ReentrantLock
import kotlin.Comparator
import kotlin.concurrent.thread

private val NODE_DISTANCE_COMPARATOR = Comparator<Node> { o1, o2 -> o1!!.distance.compareTo(o2!!.distance) }


fun shortestPathParallel(start: Node) {
    val workers = Runtime.getRuntime().availableProcessors()
    val onFinish = Phaser(workers + 1)

    val multiQueue = MultiQueue(2 * workers, NODE_DISTANCE_COMPARATOR)

    start.distance = 0
    multiQueue.add(start)
    val processingNodes = AtomicInteger(1)

    repeat(workers) {
        thread {
            while (processingNodes.get() > 0) {

                val from: Node = multiQueue.poll() ?: continue

                for (edge in from.outgoingEdges) {

                    while (true) {

                        val newDistance = from.distance + edge.weight
                        val oldDistance = edge.to.distance

                        if (oldDistance <= newDistance)
                            break

                        if (edge.to.casDistance(oldDistance, newDistance)) {
                            multiQueue.add(edge.to)
                            processingNodes.getAndIncrement()
                            break
                        }

                    }

                }
                processingNodes.getAndDecrement()

            }
            onFinish.arrive()
        }
    }
    onFinish.arriveAndAwaitAdvance()
}

class MultiQueue(private val n: Int, private val comparator: Comparator<Node>) {

    private val queues = Collections.nCopies(n, PriorityBlockingQueue(comparator))

    private val randomIndex: Int
        get() = (0 until n).random()


    private inner class PriorityBlockingQueue(comparator: Comparator<Node>) {
        val pq = PriorityQueue(comparator)
        val lock = ReentrantLock(true)

        fun peek(): Node? {
            return pq.peek()
        }
    }


    // Main operations
    fun poll(): Node? {
        while (true) {

            val first = queues[randomIndex]
            val second = queues[randomIndex]

            if (first.lock.tryLock()) {
                try {
                    return if (second.lock.tryLock()) {
                        try {
                            minOf(
                                    first,
                                    second,
                                    compareBy(nullsLast(comparator), PriorityBlockingQueue::peek)
                            ).pq.poll()
                        } finally {
                            second.lock.unlock()
                        }
                    } else first.pq.poll()
                } finally {
                    first.lock.unlock()
                }
            }
        }
    }


    fun add(node: Node) {
        while (true) {
            val index = randomIndex
            val blockingQueue = queues[index]

            if (blockingQueue.lock.tryLock()) {
                try {
                    blockingQueue.pq.add(node)
                    return
                } finally {
                    blockingQueue.lock.unlock()
                }
            }
        }
    }

}
