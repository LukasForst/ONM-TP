package onm.events

import onm.configuration.EventSeverity
import onm.house.devices.AbstractDevice
import java.util.*

/**
 * Event raised when fridge is empty.
 * */
class FridgeEmptyEvent(private val eventHandler: IEventHandler,
                       override val entityId: UUID,
                       val device: AbstractDevice) : IEvent {

    override val severity: EventSeverity
        get() = EventSeverity.INFO

    override val message: String
        get() = "Fridge is empty!"

    override fun raiseEvent() {
        eventHandler.handle(this)
    }
}