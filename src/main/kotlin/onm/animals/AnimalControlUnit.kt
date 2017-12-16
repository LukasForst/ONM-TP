package onm.animals

import onm.animals.events.AnimalBrokeSomethingEvent
import onm.animals.events.AnimalIsHungryEvent
import onm.events.DeviceBrokenEvent
import onm.events.IEventHandler
import onm.loggerFor
import java.util.*

/**
 * Animal control unit is in charge of controlling all animals in the system. It handles their events and communicates with primary event handler.
 * */
class AnimalControlUnit(private val eventHandler: IEventHandler) : IAnimalControlUnit {
    companion object {
        private val log = loggerFor(AnimalControlUnit::class.java)
    }

    private val hungryAnimals = mutableListOf<IAnimal>()
    private val animals = LinkedList<IAnimal>()

    override fun handle(event: AnimalIsHungryEvent) {
        log.info(event.message)
        hungryAnimals.add(event.animal)
        eventHandler.handle(event)
    }

    override fun handle(event: AnimalBrokeSomethingEvent) {
        log.warn(event.message)
        DeviceBrokenEvent(eventHandler, null).raiseEvent()
    }

    override fun feedAnimal(animalId: UUID) {
        val opResult = hungryAnimals.removeIf { x -> x.id == animalId }
        if (opResult) {
            log.info("Animal with UUID $animalId was removed.")
        } else {
            log.error("Animal with UUID $animalId not found in collection of hungry animals!")
        }
    }

    override fun petRandomAnimal() {
        log.info("Someone is petting an animal!")
        if (animals.size == 0) {
            log.error("No animals are instanced!")
            return
        }

        val animal = animals.pop()
        //todo add human happines
        Thread.sleep(animal.animalType.pettingTimeInMillis) //petting takes some time
        animals.push(animal)
    }
}