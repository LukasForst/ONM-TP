package onm.animals

import onm.animals.events.AnimalBrokeSomethingEvent
import onm.animals.events.AnimalIsHungryEvent
import onm.loggerFor
import java.util.*
import kotlin.concurrent.thread

/**
 * AbstractAnimal represents basics of the animals
 * */
abstract class AbstractAnimal(
        override val id: UUID,
        override val name: String,
        override val animalType: AnimalType,
        protected val eventHandler: IAnimalControlUnit) : IAnimal {

    companion object {
        protected val log = loggerFor(AbstractAnimal::class.java)
    }

    init {
        doRandomStuff() //starts doing random stuff
    }

    /**
     * This will create new thread in which will be executed never ending loop which will generates random events.
     * */
    private fun doRandomStuff() {
        thread(start = true) {
            val rd = Random()
            val brokeSmEvent = AnimalBrokeSomethingEvent(eventHandler, this, id)
            val hungryEvent = AnimalIsHungryEvent(eventHandler, this, id)

            while (true) {
                Thread.sleep((1000 * animalType.probabilityOfBrokingSomething).toLong())
                if (rd.nextInt(1000) < animalType.probabilityOfBrokingSomething) {
                    log.warn("${animalType.name} with name \"$name\" just broke something! That bastard!")
                    brokeSmEvent.raiseEvent()

                    Thread.sleep((10000 * animalType.probabilityOfBrokingSomething).toLong()) //feel guilty and to nothing
                }

                if (rd.nextInt(1000) < animalType.probabilityOfBrokingSomething * 2) {
                    log.info("${animalType.name} with name \"$name\" is hungry. Feed him.")
                    hungryEvent.raiseEvent()
                }
            }
        }
    }
}