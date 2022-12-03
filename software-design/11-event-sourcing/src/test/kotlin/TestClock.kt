import java.time.LocalDateTime
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

class TestClock internal constructor(time: LocalDateTime) : Clock() {
    private var now: Instant

    init {
        now = time.atZone(ZoneId.systemDefault()).toInstant()
    }

    fun plusHours(hours: Long) {
        now = now.plusSeconds(hours * 60 * 60)
    }

    override fun getZone(): ZoneId {
        return ZoneId.systemDefault()
    }

    override fun withZone(zone: ZoneId): Clock? {
        return null
    }

    override fun instant(): Instant {
        return now
    }
}