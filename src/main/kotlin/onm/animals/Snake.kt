package onm.animals

import java.util.*

/**
 * This is snake.
 * */
class Snake(id: UUID, name: String, eventHandler: AnimalControlUnit) : AbstractAnimal(id, name, AnimalType.SNAKE, eventHandler) {
    override fun generateReport(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}