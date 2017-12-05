package onm.house.places

import onm.configuration.PlaceType
import onm.configuration.RoomType
import onm.house.devices.AbstractDevice
import onm.house.furniture.Furniture
import onm.interfaces.Place
import java.util.*

/**
 * Room class is representing one room in the house.
 * */
class Room internal constructor(
        override val id: UUID,
        override val placeDescription: String? = null,

        /**
         * Type of room.
         * */
        val roomType: RoomType,

        /**
         * Collection of all devices in the room.
         * */
        val devicesInRoom: MutableCollection<AbstractDevice>,

        /**
         * Collection of all furniture in the room.
         * */
        val furnitureInRoom: MutableCollection<Furniture>) : Place {

    override val placeType: PlaceType
        get() = PlaceType.ROOM

    override fun generateReport(): String {
        TODO()
    }
}

class RoomBuilder(private val type: RoomType) {
    private val devicesInRoom: MutableCollection<AbstractDevice> = LinkedList()
    private val furnitureInRoom: MutableCollection<Furniture> = LinkedList()
    private var description: String? = null

    fun addDevice(device: AbstractDevice): RoomBuilder {
        devicesInRoom.add(device)
        return this
    }

    fun addFurniture(furniture: Furniture): RoomBuilder {
        furnitureInRoom.add(furniture)
        return this
    }

    fun addDescription(description: String?): RoomBuilder {
        this.description = description
        return this
    }

    fun buildRoom(): Room {
        return Room(UUID.randomUUID(), description, type, devicesInRoom, furnitureInRoom)
    }
}
