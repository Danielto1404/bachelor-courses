/**
 * В теле класса решения разрешено использовать только переменные делегированные в класс RegularInt.
 * Нельзя volatile, нельзя другие типы, нельзя блокировки, нельзя лазить в глобальные переменные.
 */
class SolutionTemplateKtKt : MonotonicClock {
    private var c1 by RegularInt(0)
    private var c2 by RegularInt(0)
    private var c3 by RegularInt(0)

    override fun write(time: Time) {
        // write right-to-left
        c3 = time.hours
        c2 = time.minutes
        c1 = time.seconds
    }

    override fun read(): Time {
        // read left-to-right
        return Time(c1, c2, c3)
    }
}