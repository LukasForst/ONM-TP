package onm.house.places

import onm.configuration.PlaceType
import onm.configuration.RoomType
import onm.house.devices.AbstractDevice
import onm.house.furniture.Furniture
import onm.interfaces.Place
import java.util.*
import kotlin.collections.ArrayList

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

        private val _devicesInRoom: MutableCollection<AbstractDevice>,
        private val _furnitureInRoom: MutableCollection<Furniture>) : Place {

    override val placeType get() = PlaceType.ROOM

    /**
     * Immutable collection of all devices in the room.
     * */
    val devicesInRoom get() = _devicesInRoom as Collection<AbstractDevice>

    /**
     * Immutable collection of all furniture in the room.
     * */
    val furnitureInRoom get() = _furnitureInRoom as Collection<Furniture>


    /**
     * Adds device to the room
     * */
    fun addDevice(device: AbstractDevice) {
        device.room = this
        _devicesInRoom.add(device)
    }

    /**
     * Adds furniture to the room
     * */
    fun addFurniture(furniture: Furniture) {
        furniture.room = this
        _furnitureInRoom.add(furniture)
    }

    override fun generateReport(): String {
        TODO()
    }

    override fun toString(): String {
        val sb = StringBuilder().append(roomType.name)

        if (placeDescription != null) {
            sb.append(" - [").append(placeDescription).append("] - ")
        }

        return sb.toString()
    }
}

class RoomBuilder(private val type: RoomType) {
    private val devicesInRoom: MutableCollection<AbstractDevice> = ArrayList()
    private val furnitureInRoom: MutableCollection<Furniture> = ArrayList()
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
        val room = Room(UUID.randomUUID(), description, type, devicesInRoom, furnitureInRoom)

        furnitureInRoom.forEach { x -> x.room = room }
        devicesInRoom.forEach { x -> x.room = room }

        return room
    }
}
