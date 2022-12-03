import event.EventStatisticsImpl
import java.time.Clock

fun main() {
    val events = EventStatisticsImpl(Clock.systemUTC())

    events.incEvent("event#1")
    events.incEvent("event#2")
    events.incEvent("event#1")
    events.incEvent("event#3")

    events.printStatistic()
}