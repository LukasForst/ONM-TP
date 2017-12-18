package onm.events

import onm.configuration.EventSeverity
import java.util.*

/**
 * Event interface has to be implemented by every single event.
 * */
interface IEvent {
    /**
     * ID of the involved entity
     * */
    val entityId: UUID
    /**
     * Message of the event.
     * */
    val message: String

    /**
     * Severity of the event. Used for filtering events by severity levels.
     * */
    val severity: EventSeverity

    /**
     * Handle event by eventHandler
     * */
    fun raiseEvent()
}