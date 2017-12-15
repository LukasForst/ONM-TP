package onm.animals

import onm.interfaces.MovableEntity

/**
 * Interface represents methods and properties that every animal have
 * */
interface IAnimal : MovableEntity {

    /**
     * Name of the animal
     * */
    val name: String

    /**
     * Type of animal
     * */
    val animalType: AnimalType
}