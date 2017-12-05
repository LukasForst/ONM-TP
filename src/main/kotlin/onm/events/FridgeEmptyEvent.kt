package onm.events

import onm.configuration.EventSeverity
import onm.interfaces.EventHandler

/**
 * Event raised when fridge is empty.
 * */
class FridgeEmptyEvent(
        override val message: String,
        override val severity: EventSeverity,

        private val eventHandler: EventHandler) : Event {

    override fun raiseEvent() {
        eventHandler.handle(this)
    }
}