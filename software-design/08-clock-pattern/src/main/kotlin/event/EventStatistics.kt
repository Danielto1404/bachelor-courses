package event

interface EventStatistics {
    fun incEvent(name: String)
    fun getEventStatisticByName(name: String): Double
    val allEventStatistic: Map<String, Double>
    fun printStatistic()
}