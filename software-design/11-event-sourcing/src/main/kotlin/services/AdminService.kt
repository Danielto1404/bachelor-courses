package services

import storage.EventStorage
import java.time.Clock
import events.AccountCreationEvent
import java.time.LocalDateTime
import events.ExtendExpirationEvent
import models.Account
import java.lang.RuntimeException
import events.VisitEvent

class AdminService(private val eventStorage: EventStorage, private val clock: Clock) {

    fun createAccount(id: Int) {
        eventStorage.save(id, AccountCreationEvent(LocalDateTime.now(clock)))
    }

    fun extendExpiration(id: Int, newExpirationDate: LocalDateTime) {
        eventStorage.save(id, ExtendExpirationEvent(newExpirationDate))
    }

    fun getAccountInfo(id: Int): Account? {
        val events = eventStorage.getAccountEvents(id) ?: return null

        if (events.first() !is AccountCreationEvent) {
            throw RuntimeException("Account is not created")
        }

        var account: Account? = null
        for (event in events) {
            when (event) {
                is AccountCreationEvent -> {
                    account = Account(id, event.creationDate)
                }
                is ExtendExpirationEvent -> {
                    assert(account != null)
                    account?.expirationDate = event.expirationDate
                }
                is VisitEvent -> {
                    assert(account != null)
                    account?.lastVisitDate = event.visitDate
                }
            }
        }
        return account
    }
}