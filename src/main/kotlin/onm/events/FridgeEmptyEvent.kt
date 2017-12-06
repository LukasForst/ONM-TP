package onm.events

import onm.configuration.EventSeverity
import onm.interfaces.EventHandler

/**
 * Event raised when fridge is empty.
 * */
class FridgeEmptyEvent(private val eventHandler: EventHandler) : Event {

    override val severity: EventSeverity
        get() = EventSeverity.INFO

    override val message: String
        get() = "Fridge is empty!"

    override fun raiseEvent() {
        eventHandler.handle(this)
    }
}