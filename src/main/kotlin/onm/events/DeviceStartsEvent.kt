package onm.events


import onm.configuration.EventSeverity
import java.util.*

class DeviceStartsEvent(
        private val eventHandler: IEventHandler,
        override val entityId: UUID,
        override val message: String) : IEvent {

    override fun raiseEvent() {
        eventHandler.handle(this)
    }

    override val severity: EventSeverity
        get() = EventSeverity.INFO

}