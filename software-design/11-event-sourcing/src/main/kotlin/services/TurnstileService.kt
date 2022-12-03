package services

import events.AccountCreationEvent
import events.ExitEvent
import events.ExtendExpirationEvent
import events.VisitEvent
import storage.EventStorage
import java.time.Clock
import java.time.LocalDateTime

class TurnstileService(private val eventStorage: EventStorage, private val clock: Clock) {

    fun visit(id: Int) {
        if (isExpired(id)) {
            throw RuntimeException("Account is expired")
        }
        eventStorage.save(id, VisitEvent(LocalDateTime.now(clock)))
    }

    fun exit(id: Int) {
        eventStorage.save(id, ExitEvent(LocalDateTime.now(clock)))
    }

    private fun isExpired(id: Int): Boolean {
        val events = eventStorage.getAccountEvents(id) ?: throw RuntimeException("No event found for accountId=$id")
        if (events.first() !is AccountCreationEvent) {
            throw RuntimeException("Account creation event not found")
        }
        var expirationDate: LocalDateTime? = null
        for (event in events) {
            if (event is AccountCreationEvent) {
                expirationDate = event.creationDate
            } else if (event is ExtendExpirationEvent) {
                expirationDate = event.expirationDate
            }
        }
        return LocalDateTime.now(clock).isAfter(expirationDate)
    }
}