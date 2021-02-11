package dijkstra

import kotlinx.atomicfu.atomic
import java.util.*
import java.util.concurrent.Phaser
import java.util.concurrent.PriorityBlockingQueue
import kotlin.Comparator
import kotlin.concurrent.thread

private val NODE_DISTANCE_COMPARATOR = Comparator<Node> { o1, o2 ->
    o1!!.distance.compareTo(o2!!.distance)
}


fun shortestPathParallel(start: Node) {

    val workers = Runtime.getRuntime().availableProcessors()

    start.distance = 0

    val processingNodes = atomic(1)
    val multiQueue = RandomMultiQueue(2 * workers, NODE_DISTANCE_COMPARATOR)

    multiQueue.add(start)

    val onFinish = Phaser(workers + 1)

    repeat(workers) {
        thread {
            while (true) {
                val from = multiQueue.poll() ?: if (processingNodes.value == 0) break else continue

                for (edge in from.outgoingEdges) {

                    val actualDistance = from.distance + edge.weight
                    var oldDistance = edge.to.distance

                    while (oldDistance > actualDistance) {
                        if (edge.to.casDistance(oldDistance, actualDistance)) {
                            multiQueue.add(edge.to)
                            processingNodes.getAndIncrement()
                        }
                        oldDistance = edge.to.distance
                    }
                }

                processingNodes.getAndDecrement()

            }

            onFinish.arrive()
        }
    }
    onFinish.arriveAndAwaitAdvance()
}


private class RandomMultiQueue<E>(val n: Int, comparator: Comparator<E>) {

    private val queues = Collections.nCopies(n, PriorityBlockingQueue<E>(256, comparator))
    private val randomGenerator = Random(239)

    fun poll(): E? {
        return queues[randomGenerator.nextInt(n)].poll()
    }

    fun add(e: E) {
        queues[randomGenerator.nextInt(n)].add(e)
    }
}
