package onm.events

import onm.configuration.EventSeverity

/**
 * Event raised when oven finishes baking.
 * */
class BakeFinishedEvent(private val eventHandler: IEventHandler) : IEvent {

    override val severity: EventSeverity
        get() = EventSeverity.EXECUTION_DONE

    override val message: String
        get() = "Oven finished baking!"

    override fun raiseEvent() {
        eventHandler.handle(this)
    }
}