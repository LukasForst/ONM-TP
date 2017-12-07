package onm.events

import onm.configuration.EventSeverity

/**
 * IEvent raised when fridge is empty.
 * */
class FridgeEmptyIEvent(private val IEventHandler: IEventHandler) : IEvent {

    override val severity: EventSeverity
        get() = EventSeverity.INFO

    override val message: String
        get() = "Fridge is empty!"

    override fun raiseEvent() {
        IEventHandler.handle(this)
    }
}