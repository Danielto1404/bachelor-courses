package services

import events.Event
import events.ExitEvent
import events.VisitEvent
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class ReportService {
    private val lastVisits: MutableMap<Int, LocalDateTime> = HashMap()
    val dailyStats: MutableMap<LocalDate, MutableList<Int>> = HashMap()

    fun handleEvent(id: Int, event: Event?) {
        if (event is VisitEvent) {
            lastVisits[id] = event.visitDate
        } else if (event is ExitEvent) {
            val visitTime = lastVisits[id]
            val exitTime = event.exitDate
            val duration = ChronoUnit.MINUTES.between(visitTime, exitTime).toInt()
            val curDay = visitTime!!.toLocalDate()
            if (!dailyStats.containsKey(curDay)) {
                dailyStats[curDay] = ArrayList()
            }
            dailyStats[curDay]!!.add(duration)
        }
    }

    val averageDuration: Double
        get() = dailyStats
            .values
            .stream()
            .flatMap { it.stream() }
            .mapToDouble { it.toDouble() }
            .average().orElse(0.0)

    val averageDailyVisits: Double
        get() {
            val visits = dailyStats
                .values
                .stream()
                .mapToInt() { it.size }
                .sum()

            return visits.toDouble() / dailyStats.size
        }
}