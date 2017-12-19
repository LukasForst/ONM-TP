package onm.animals

import onm.interfaces.HouseEntity

/**
 * Interface represents methods and properties that every animal have
 * */
interface IAnimal : HouseEntity {

    /**
     * Name of the animal
     * */
    val name: String

    /**
     * Type of animal
     * */
    val animalType: AnimalType
}