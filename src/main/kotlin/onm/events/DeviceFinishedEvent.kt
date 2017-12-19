package onm.events

import onm.configuration.EventSeverity
import onm.house.devices.AbstractDevice
import java.util.*

class DeviceFinishedEvent(
        private val eventHandler: IEventHandler,
        override val entityId: UUID,
        override val message: String,
        val device: AbstractDevice) : IEvent {


    override fun raiseEvent() {
        eventHandler.handle(this)
    }

    override val severity: EventSeverity
        get() = EventSeverity.EXECUTION_DONE

}