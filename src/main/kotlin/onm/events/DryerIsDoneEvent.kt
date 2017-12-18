package onm.events

import onm.configuration.EventSeverity
import java.util.*

class DryerIsDoneEvent(private val eventHandler: IEventHandler, override val entityId: UUID) : IEvent {
    override val severity: EventSeverity
        get() = EventSeverity.INFO

    override val message: String
        get() = "Dryer is done!"

    override fun raiseEvent() {
        eventHandler.handle(this)
    }
}