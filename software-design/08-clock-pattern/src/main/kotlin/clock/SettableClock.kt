package clock

import java.time.Clock
import java.time.Instant
import java.time.ZoneId

class SettableClock(private var now: Instant) : Clock() {
    override fun getZone(): ZoneId = ZoneId.systemDefault()
    override fun withZone(zone: ZoneId): Clock = throw UnsupportedOperationException()
    override fun instant(): Instant = now
    fun set(now: Instant) {
        this.now = now
    }
}