package onm.house.furniture

import onm.configuration.FurnitureType
import onm.house.places.Room
import onm.interfaces.StationaryEntity
import java.util.*

/**
 * Class representing furniture in the house. Types of furniture should implements this class.
 * */
class Furniture internal constructor(
        override val id: UUID,
        val furnitureType: FurnitureType) : StationaryEntity {
    //todo implement and get specs

    /**
     * Room reference. This should be set after adding furniture to the room.
     * */
    var room: Room? = null

    override fun generateReport(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

object FurnitureFactory {
    fun createChair(): Furniture {
        return Furniture(UUID.randomUUID(), FurnitureType.CHAIR)
    }
}