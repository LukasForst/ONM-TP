package onm.animals.events

import onm.animals.IAnimal
import onm.animals.IAnimalControlUnit
import onm.configuration.EventSeverity
import onm.events.IEvent
import java.util.*

/**
 * Event which is raised when some animal broke some device or other thing.
 * */
class AnimalBrokeSomethingEvent(
        private val eventHandler: IAnimalControlUnit,

        /**
         * Animal which raised this event.
         * */
        val animal: IAnimal, override val entityId: UUID) : IEvent {

    override val message: String
        get() = "Pet \"" + animal.name + "\" broke somthing!"
    override val severity: EventSeverity
        get() = EventSeverity.DEVICE_BROKEN

    override fun raiseEvent() {
        eventHandler.handle(this)
    }
}