package onm.animals

import onm.animals.events.AnimalBrokeSomethingEvent
import onm.animals.events.AnimalIsHungryEvent
import onm.configuration.EventSeverity
import onm.events.DeviceBrokenEvent
import onm.events.EventHandler
import onm.events.IEventHandler
import onm.loggerFor
import onm.reports.AnimalReport
import onm.reports.CentralLogUnit
import java.time.Instant
import java.util.*
import java.util.concurrent.ConcurrentLinkedDeque
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * Animal control unit is in charge of controlling all animals in the system. It handles their events and communicates with primary event handler.
 * */
class AnimalControlUnit(private val eventHandler: IEventHandler) : IAnimalControlUnit {
    companion object {
        val instance by lazy { AnimalControlUnit(EventHandler.instance) }
        private val log = loggerFor(AnimalControlUnit::class.java)
    }

    private val hungryAnimals = ConcurrentLinkedQueue<IAnimal>()
    private val animals = ConcurrentLinkedDeque<IAnimal>()

    private val logUnit = CentralLogUnit.instance

    init {
        eventHandler.register(this)
    }

    override fun register(animal: IAnimal) {
        animals.add(animal)
    }

    override fun handle(event: AnimalIsHungryEvent) {
        log.info(event.message)
        hungryAnimals.add(event.animal)
        eventHandler.handle(event)
    }

    override fun handle(event: AnimalBrokeSomethingEvent) {
        log.warn(event.message)
        DeviceBrokenEvent(eventHandler, null, event.entityId).raiseEvent()
    }

    override fun feedAnimal(animalId: UUID) {
        val animal = hungryAnimals.firstOrNull { x -> x.id == animalId }
        if (animal != null) {
            log.info("Animal with UUID $animalId was feed up!.")
            hungryAnimals.removeIf { x -> x.id == animalId }
            logUnit.addReport(AnimalReport(Instant.now(), animal.id, "Feeding animal", EventSeverity.INFO, animal.animalType))
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

        logUnit.addReport(AnimalReport(Instant.now(), animal.id, "Petting animal.", EventSeverity.INFO, animal.animalType))
        Thread.sleep(animal.animalType.pettingTimeInMillis) //petting takes some time
        animals.push(animal)
    }
}