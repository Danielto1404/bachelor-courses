package ru.itmo.ct.danielto.codingtheory.finitefields

import java.lang.Integer.max

class FiniteField(private val p: Int, private val module: Polynomial) {
    infix fun Int.plus(other: Int): Int = (this + other) % p

    infix fun Int.minus(other: Int): Int = (this - other + p) % p

    infix fun Int.times(other: Int): Int = (this * other) % p

    infix fun Int.div(other: Int): Int = this times other.reversed()

    fun Int.reversed(): Int = (gcd(p, this).second + p) % p

    private fun gcd(a: Int, b: Int): Triple<Int, Int, Int> {
        if (a == 0) {
            return Triple(0, 1, b)
        }
        val (x1, y1, d) = gcd(b % a, a)
        return Triple(y1 - (b / a) * x1, x1, d)
    }

    operator fun Polynomial.plus(other: Polynomial): Polynomial = plusOrMinus(other) { a, b -> a plus b }

    operator fun Polynomial.minus(other: Polynomial): Polynomial = plusOrMinus(other) { a, b -> a minus b }

    infix fun Polynomial.shift(shift: Int) = if (isZero) this else
        Polynomial(List(coefficients.size + shift) { i -> coefficients.getOrElse(i - shift) { 0 } })

    operator fun Polynomial.times(other: Polynomial) = timesWithoutModulo(other).modulo()

    operator fun Polynomial.times(constant: Int): Polynomial {
        return Polynomial(coefficients.map { constant times it })
    }

    fun Polynomial.reversed() = gcd(module, this).second

    operator fun Polynomial.div(other: Polynomial) = this * other.reversed()

    fun Polynomial.next(): Polynomial {
        if (coefficients.isEmpty()) {
            return Polynomial(0 to 1)
        }
        val newCoefficients = coefficients.toMutableList()
        newCoefficients[0]++
        for (i in newCoefficients.indices) {
            if (newCoefficients[i] == p) {
                newCoefficients[i] = 0
                if (i == newCoefficients.lastIndex) {
                    newCoefficients.add(1)
                } else {
                    newCoefficients[i + 1] += 1
                }
            } else {
                break
            }
        }
        return Polynomial(newCoefficients)
    }

    private infix fun Polynomial.timesWithoutModulo(other: Polynomial): Polynomial {
        if (isZero) return this
        if (other.isZero) return other
        val newCoefficients = MutableList(coefficients.size + other.coefficients.size - 1) { 0 }
        for (i in coefficients.indices) {
            for (j in other.coefficients.indices) {
                newCoefficients[i + j] = newCoefficients[i + j] plus (coefficients[i] times other.coefficients[j])
            }
        }
        return Polynomial(newCoefficients)
    }

    private fun Polynomial.modulo(): Polynomial = divAndRem(module).second

    private fun Polynomial.plusOrMinus(other: Polynomial, operation: (Int, Int) -> Int): Polynomial {
        var maxI = 0
        val newCoefficients = MutableList(max(coefficients.size, other.coefficients.size)) { 0 }
        for (i in newCoefficients.indices) {
            newCoefficients[i] = operation(coefficients.getOrElse(i) { 0 }, other.coefficients.getOrElse(i) { 0 })
            if (newCoefficients[i] > 0) {
                maxI = i + 1
            }
        }
        return Polynomial(newCoefficients.subList(0, maxI))
    }

    private fun Polynomial.divAndRem(other: Polynomial): Pair<Polynomial, Polynomial> {
        var dividend = this
        val quotientCoefficients = MutableList(max(coefficients.size - other.coefficients.size + 1, 0)) { 0 }
        while (dividend.coefficients.size >= other.coefficients.size) {
            val coefficient = dividend.coefficients.last() div
                    other.coefficients.last()
            val shift = dividend.coefficients.size - other.coefficients.size
            quotientCoefficients[shift] = coefficient
            dividend -= (other * coefficient shift shift)
        }
        return Polynomial(quotientCoefficients) to dividend
    }

    private fun gcd(a: Polynomial, b: Polynomial): Triple<Polynomial, Polynomial, Polynomial> {
        if (a.isZero) {
            return Triple(Polynomial(listOf()), Polynomial(listOf(1)), b)
        }
        val divisionResult = b.divAndRem(a)
        val (x1, y1, d) = gcd(divisionResult.second, a)
        return Triple(y1 - (divisionResult.first timesWithoutModulo x1), x1, d)
    }

    fun <T> inField(block: FiniteField.() -> T): T = block()
}