package onm.events

import onm.configuration.EventSeverity
import onm.house.devices.AbstractDevice
import java.util.*

class RepairEvent (private val eventHandler: IEventHandler,
                   /**
                    * Reference to device which is broken.
                    * */
                   val device: AbstractDevice, override val entityId: UUID) : IEvent { //todo probably will be better to use api

    override val message: String
        get() = "Device with description: " + device.deviceDescription + " is working again!"

    override val severity: EventSeverity
        get() = EventSeverity.DEVICE_BROKEN

    override fun raiseEvent() {
        eventHandler.handle(this)
    }
}