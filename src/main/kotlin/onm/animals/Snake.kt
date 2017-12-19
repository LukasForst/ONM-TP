package onm.animals

import onm.reports.IReport
import java.util.*

/**
 * This is snake.
 * */
class Snake(id: UUID, name: String, eventHandler: AnimalControlUnit) : AbstractAnimal(id, name, AnimalType.SNAKE, eventHandler) {
    init {
        eventHandler.register(this)
    }

    override fun generateReport(): IReport {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}