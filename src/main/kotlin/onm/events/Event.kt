package onm.events

import onm.configuration.EventSeverity

/**
 * Event interface has to be implemented by every single event. Equals and Hascode method should be generated because of the tests.
 * */
interface Event {

    /**
     * Message of the event.
     * */
    val message: String

    /**
     * Severity of the event. Used for filtering events by severity levels.
     * */
    val severity: EventSeverity

    /**
     * Handle event by EventHandler
     * */
    fun raiseEvent()
}