package onm.events

import onm.configuration.EventSeverity
import onm.interfaces.EventHandler

/**
 * Event raised when oven finishes baking.
 * */
class BakeFinishedEvent(private val eventHandler: EventHandler) : Event {

    override val severity: EventSeverity
        get() = EventSeverity.INFO

    override val message: String
        get() = "Oven finished baking!"

    override fun raiseEvent() {
        eventHandler.handle(this)
    }
}