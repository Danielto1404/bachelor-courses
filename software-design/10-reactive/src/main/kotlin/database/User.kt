package database

import org.bson.Document

class User(private val id: Int, private val name: String, val currency: String) {
    constructor(doc: Document) : this(
        id = doc.getInteger(ID_FIELD),
        name = doc.getString(NAME_FIELD),
        currency = doc.getString(CURRENCY_FIELD)
    )

    val document: Document
        get() = Document(ID_FIELD, id).append(NAME_FIELD, name).append(CURRENCY_FIELD, currency)

    override fun toString(): String {
        return """
               User{userId=$id, name='$name', currency='${currency}'}
               """.trimIndent()
    }

    companion object {
        const val ID_FIELD = "userId"
        const val NAME_FIELD = "name"
        const val CURRENCY_FIELD = "currency"
    }
}