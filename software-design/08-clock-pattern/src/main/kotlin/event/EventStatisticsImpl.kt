package event

import java.time.Clock
import java.time.Duration
import java.time.Instant
import java.util.stream.Collectors

class EventStatisticsImpl(private val clock: Clock) : EventStatistics {
    private val events: MutableMap<String, MutableList<Instant>> = HashMap()

    override fun incEvent(name: String) {
        val instants = events.getOrDefault(name, arrayListOf())
        instants.add(clock.instant())
        events[name] = instants
    }

    override fun getEventStatisticByName(name: String): Double {
        val instants = events[name] ?: return 0.0
        return getLastHourRPM(instants)
    }

    override val allEventStatistic: Map<String, Double>
        get() {
            val stats: MutableMap<String, Double> = HashMap()
            events.forEach { (k, v) -> stats[k] = getLastHourRPM(v) }
            return stats
        }

    override fun printStatistic() {
        allEventStatistic.forEach { (k, v) -> println("$k : $v") }
    }

    private fun getLastHourRPM(instants: List<Instant>): Double {
        val instantHourMinus = clock.instant().minus(Duration.ofHours(1))
        val filteredInstants = instants.stream()
            .filter { x: Instant -> x.isAfter(instantHourMinus) }
            .collect(Collectors.toList())
        return filteredInstants.size / 60.0
    }
}