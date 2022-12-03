package models

import java.time.LocalDateTime

class Account(val id: Int, val createdDate: LocalDateTime) {
    var expirationDate: LocalDateTime = createdDate
    var lastVisitDate: LocalDateTime? = null
}