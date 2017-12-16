package onm.animals.events

import onm.animals.IAnimal
import onm.animals.IAnimalControlUnit
import onm.configuration.EventSeverity
import onm.events.IEvent

class AnimalIsHungryEvent(private val eventHandler: IAnimalControlUnit,
                          /**
                           * Animal which raised this event.
                           * */
                          val animal: IAnimal) : IEvent {

    override val message: String
        get() = "Pet \"" + animal.name + "\" is hungry!"
    override val severity: EventSeverity
        get() = EventSeverity.WARNING

    override fun raiseEvent() {
        eventHandler.handle(this)
    }
}