package storage

import events.Event
import services.ReportService

class EventStorage {
    private val events: MutableMap<Int, MutableList<Event>> = HashMap()
    private var reportService: ReportService? = null

    fun save(id: Int, event: Event) {
        if (!events.containsKey(id)) {
            events[id] = ArrayList()
        }
        events[id]?.add(event)
        reportService?.handleEvent(id, event)
    }

    fun getAccountEvents(id: Int): List<Event>? {
        return events[id]
    }

    fun subscribe(reportService: ReportService) {
        this.reportService = reportService
        events.forEach { (id, accountEvents) ->
            accountEvents.forEach { event ->
                reportService.handleEvent(id, event)
            }
        }
    }
}