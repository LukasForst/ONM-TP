package onm.events

import onm.configuration.EventSeverity

class DryerIsDoneEvent(private val eventHandler: IEventHandler) : IEvent {
    override val severity: EventSeverity
        get() = EventSeverity.INFO

    override val message: String
        get() = "Dryer is done!"

    override fun raiseEvent() {
        eventHandler.handle(this)
    }
}