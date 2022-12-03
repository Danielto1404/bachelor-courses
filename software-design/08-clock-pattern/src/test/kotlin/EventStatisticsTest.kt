import clock.SettableClock
import event.EventStatistics
import event.EventStatisticsImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.Instant

class EventStatisticsTest {
    private var clock: SettableClock = SettableClock(Instant.now())
    private var eventStats: EventStatistics = EventStatisticsImpl(clock)

    @BeforeEach
    fun setup() {
        clock = SettableClock(Instant.now())
        eventStats = EventStatisticsImpl(clock)
    }

    @Test
    fun emptyEventByNameTest() {
        assertEquals(0.0, eventStats.getEventStatisticByName("non-existent"))
    }

    @Test
    fun singleEventByNameTest() {
        eventStats.incEvent("event")
        assertEquals(1 / 60.0, eventStats.getEventStatisticByName("event"))
    }

    @Test
    fun multiEventByNameTest() {
        eventStats.incEvent("event#1")
        eventStats.incEvent("event#2")
        eventStats.incEvent("event#1")
        eventStats.incEvent("event#3")
        assertEquals(2 / 60.0, eventStats.getEventStatisticByName("event#1"))
    }

    @Test
    fun lastHourEventByNameTest() {
        eventStats.incEvent("event#1")
        eventStats.incEvent("event#1")
        clock.set(Instant.now().plus(Duration.ofHours(1)))
        eventStats.incEvent("event#1")
        eventStats.incEvent("event#3")
        assertEquals(1 / 60.0, eventStats.getEventStatisticByName("event#1"))
    }

    @Test
    fun allEventsTest() {
        val now = Instant.now()
        clock.set(now.minus(Duration.ofHours(2)))
        eventStats.incEvent("event#1")
        clock.set(now.minus(Duration.ofHours(1)))
        eventStats.incEvent("event#2")
        clock.set(now.minus(Duration.ofMinutes(30)))
        eventStats.incEvent("event#3")
        eventStats.incEvent("event#1")
        clock.set(now.minus(Duration.ofMinutes(10)))
        eventStats.incEvent("event#1")
        clock.set(now.minus(Duration.ofMinutes(1)))
        eventStats.incEvent("event#2")
        clock.set(now)
        eventStats.incEvent("event#1")
        val expectedMap = mapOf(
            "event#1" to 3 / 60.0,
            "event#2" to 1 / 60.0,
            "event#3" to 1 / 60.0
        )
        assertEquals(expectedMap, eventStats.allEventStatistic)
    }
}