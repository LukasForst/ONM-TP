package onm.animals

import onm.animals.events.AnimalBrokeSomethingEvent
import onm.animals.events.AnimalIsHungryEvent
import java.util.*

interface IAnimalControlUnit {
    fun register(animal: IAnimal)

    fun handle(event: AnimalIsHungryEvent)

    fun handle(event: AnimalBrokeSomethingEvent)

    fun feedAnimal(animalId: UUID)

    fun petRandomAnimal()
}