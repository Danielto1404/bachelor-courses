/**
 * В теле класса решения разрешено использовать только переменные делегированные в класс RegularInt.
 * Нельзя volatile, нельзя другие типы, нельзя блокировки, нельзя лазить в глобальные переменные.
 *
 * @author : Korolev Daniil
 */

class Solution : MonotonicClock {

    private var actualHours by RegularInt(0)
    private var actualMinutes by RegularInt(0)
    private var actualSeconds by RegularInt(0)
    private var hoursSnapshot by RegularInt(0)
    private var minutesSnapshot by RegularInt(0)

    override fun write(time: Time) {
        // write right-to-left
        hoursSnapshot = time.hours
        minutesSnapshot = time.minutes

        actualSeconds = time.seconds
        actualMinutes = time.minutes
        actualHours = time.hours
    }

    override fun read(): Time {
        // read left-to-right

        val MAX_TIME = Integer.MAX_VALUE

        val hours = actualHours
        val minutes = actualMinutes
        val seconds = actualSeconds

        val hoursSnap = hoursSnapshot
        val minutesSnap = minutesSnapshot

        return when {
            hours == hoursSnap && minutes == minutesSnap -> Time(hours, minutes, seconds)
            hours == hoursSnap && minutes != minutesSnap -> Time(hours, minutes, MAX_TIME)
            else -> Time(hours, MAX_TIME, MAX_TIME)
        }
    }
}

