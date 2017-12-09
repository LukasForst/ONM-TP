package onm.events

import onm.configuration.EventSeverity

class KettleDoneEvent(private val eventHandler: IEventHandler) : IEvent {

    override val message: String
        get() = "Water is 100 degree of C!"

    override val severity: EventSeverity
        get() = EventSeverity.EXECUTION_DONE

    override fun raiseEvent() {
        eventHandler.handle(this)
    }
}