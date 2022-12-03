package events

import java.time.LocalDateTime

class ExtendExpirationEvent(val expirationDate: LocalDateTime) : Event