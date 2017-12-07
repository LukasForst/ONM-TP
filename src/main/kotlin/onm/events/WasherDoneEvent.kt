package onm.events

import onm.configuration.EventSeverity

/**
 * Washing machine has done its job event.
 * */
class WasherDoneEvent(private val eventHandler: IEventHandler) : IEvent {

    override val message: String
        get() = "Your clothes are washed!"

    override val severity: EventSeverity
        get() = EventSeverity.EXECUTION_DONE

    override fun raiseEvent() {
        eventHandler.handle(this)
    }
}