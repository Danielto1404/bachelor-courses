import ru.itmo.ct.danielto.codingtheory.finitefields.FiniteField
import ru.itmo.ct.danielto.codingtheory.finitefields.Polynomial
import kotlin.math.pow

fun task1() {
    val p = 2
    val module = Polynomial(
        5 to 1,
        2 to 1,
        0 to 1
    )

    println("-----task 1-----")
    println("p: $p")
    println("module: $module")
    println()

    val result = FiniteField(p, module).inField {
        Polynomial(22 to 1) / (Polynomial(16 to 1) * Polynomial(20 to 1) + Polynomial(0 to 1))
    }

    println("result: $result")
    println()
}

fun task2() {
    val p = 5
    val module = Polynomial(
        3 to 1,
        2 to 1,
        1 to 3,
        0 to 4
    )
    println("-----task 2-----")
    println("p: $p")
    println("module: $module")
    println()

    val result = FiniteField(p, module).inField {
        val one = Polynomial(0 to 1)
        var primitiveElement = one
        val expectedCount = p.toDouble().pow(module.pow.toDouble()).toInt() - 1
        while (true) {
            var actualPow = 1
            var polynomial = primitiveElement
            while (polynomial != one) {
                actualPow++
                polynomial *= primitiveElement
            }
            if (actualPow == expectedCount) {
                break
            } else {
                primitiveElement = primitiveElement.next()
            }
        }
        primitiveElement
    }

    println("result: $result")
    println()
}

fun task3() {
    val p = 2
    val dividend = Polynomial(
        9 to 1,
        8 to 1,
        2 to 1,
        1 to 1
    )
    val divisor = Polynomial(
        9 to 1,
        8 to 1,
        4 to 1,
        3 to 1,
        1 to 1
    )
    val module = Polynomial(
        10 to 1,
        9 to 1,
        8 to 1,
        7 to 1,
        6 to 1,
        2 to 1,
        0 to 1,
    )

    println("-----task 3-----")
    println("p: $p")
    println("dividend: $dividend")
    println("divisor: $divisor")
    println("module: $module")
    println()

    val result = FiniteField(p, module).inField { dividend / divisor }

    println("result: $result")
    println()
}

fun main() {
    task1()
    task2()
    task3()
}