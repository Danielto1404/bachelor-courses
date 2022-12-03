package database

import org.bson.Document

class Product(private val name: String, private val price: Double) {

    internal constructor(doc: Document) : this(doc.getString(NAME_FIELD), doc.getDouble(PRICE_FIELD))

    val document: Document
        get() = Document(NAME_FIELD, name).append(PRICE_FIELD, price)

    private fun convertPrice(currency: String): Double {
//      (Oh dear, sanctions...)
        val ratio = when (currency) {
            "usd" -> 112.0
            "eur" -> 125.0
            else -> 1.0
        }
        return price / ratio
    }

    fun toString(currency: String): String {
        return """
               Product{name=$name, price in rub=$price, price in $currency=${convertPrice(currency)}}
               """.trimIndent()
    }

    companion object {
        const val NAME_FIELD = "name"
        const val PRICE_FIELD = "price"
    }
}