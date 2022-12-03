package events

import java.time.LocalDateTime

class AccountCreationEvent(val creationDate: LocalDateTime) : Event