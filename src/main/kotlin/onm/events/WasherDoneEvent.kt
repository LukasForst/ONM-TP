package onm.events

import onm.configuration.EventSeverity
import onm.interfaces.EventHandler

/**
 * Washing mashine has done its job event.
 * */
class WasherDoneEvent(
        override val message: String,
        override val severity: EventSeverity,

        private val eventHandler: EventHandler) : Event {

    override fun raiseEvent() {
        eventHandler.handle(this)
    }
}