package onm.events

import onm.configuration.EventSeverity
import onm.house.devices.AbstractDevice

class RepairEvent (private val eventHandler: IEventHandler,
                   /**
                    * Reference to device which is broken.
                    * */
                   val device: AbstractDevice?) : IEvent { //todo probably will be better to use api

    override val message: String
        get() {
            return if (device != null) {
                "Device with description: " + device.deviceDescription + " is working again!"
            } else {
                "Random device is working again!"
            }
        }
    override val severity: EventSeverity
        get() = EventSeverity.DEVICE_BROKEN

    override fun raiseEvent() {
        eventHandler.handle(this)
    }
}