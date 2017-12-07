package onm.events

import onm.configuration.EventSeverity

/**
 * Washing machine has done its job event.
 * */
class WasherDoneIEvent(private val IEventHandler: IEventHandler) : IEvent {

    override val message: String
        get() = "Your clothes are washed!"

    override val severity: EventSeverity
        get() = EventSeverity.EXECUTION_DONE

    override fun raiseEvent() {
        IEventHandler.handle(this)
    }
}