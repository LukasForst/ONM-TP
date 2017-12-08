package onm.events

import onm.configuration.EventSeverity

/**
 * Event interface has to be implemented by every single event.
 * */
interface IEvent {

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