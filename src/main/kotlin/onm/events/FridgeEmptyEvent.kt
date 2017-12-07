package onm.events

import onm.configuration.EventSeverity

/**
 * Event raised when fridge is empty.
 * */
class FridgeEmptyEvent(private val eventHandler: IEventHandler) : IEvent {

    override val severity: EventSeverity
        get() = EventSeverity.INFO

    override val message: String
        get() = "Fridge is empty!"

    override fun raiseEvent() {
        eventHandler.handle(this)
    }
}