package onm.animals

import java.util.*

/**
 * This is cat.
 * */
class Cat(id: UUID, name: String, eventHandler: AnimalControlUnit) : AbstractAnimal(id, name, AnimalType.CAT, eventHandler) {
    override fun generateReport(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}