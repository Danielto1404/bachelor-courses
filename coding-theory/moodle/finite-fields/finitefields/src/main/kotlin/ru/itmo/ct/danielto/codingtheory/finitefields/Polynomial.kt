package ru.itmo.ct.danielto.codingtheory.finitefields

import java.lang.IllegalArgumentException

data class Polynomial(val coefficients: List<Int>) {

    init {
        if (coefficients.isNotEmpty() && coefficients.last() == 0) {
            throw IllegalArgumentException("coefficient of highest power can't be 0")
        }
    }

    constructor(elements: Map<Int, Int>) : this(
        (0..(elements.keys.maxOrNull() ?: 0))
            .mapNotNull { elements.getOrDefault(it, 0) }
            .toList()
    )

    /**
     * Main constructor to create polynomial "by hands"
     * @param elements representation of sum of `a_i * x^i where `i` is the first element of pair and `a_i` is the second
     */
    constructor(vararg elements: Pair<Int, Int>) : this(elements.toMap())

    val isZero = coefficients.isEmpty()
    val pow = if (coefficients.isEmpty()) 0 else coefficients.size - 1

    override fun toString(): String = if (isZero) "0" else
        coefficients
            .withIndex()
            .filter { it.value != 0 }
            .joinToString(" + ") { "${it.value}*x^${it.index}" }
}