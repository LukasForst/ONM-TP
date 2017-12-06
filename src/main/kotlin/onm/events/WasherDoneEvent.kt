package onm.events

import onm.configuration.EventSeverity
import onm.interfaces.EventHandler

/**
 * Washing machine has done its job event.
 * */
class WasherDoneEvent(private val eventHandler: EventHandler) : Event {

    override val message: String
        get() = "Your clothes are washed!"

    override val severity: EventSeverity
        get() = EventSeverity.EXECUTION_DONE

    override fun raiseEvent() {
        eventHandler.handle(this)
    }
}