package onm.builder

import onm.configuration.RoomType
import onm.events.EventHandler
import onm.house.devices.Fridge
import onm.house.devices.Washer
import onm.house.places.RoomBuilder
import java.util.*

/**
 * Class representing framework builder for devices and rooms in a smart house.
 */
class HouseBuilder(){


    /**
     * IEvent handler dealing with all raised events from i.e., devices
     */
    val eventHandler : EventHandler = EventHandler()
    /**
     * House variable containing references to all rooms
     */
    val house : House = House()

    /**
     * If there is not a room with given type, the room is created and added to the house
     * @param type Type of desired room
     */
    fun addRoom(type : RoomType){

        if(!house.rooms.any { room -> room.roomType == type })
            house.rooms.add(RoomBuilder(type).buildRoom())
    }

    /**
     * Whether the room exists, it creates a fridge and puts it in
     * @param roomType where to put fridge
     * @return created fridge
     */
    fun addFridge(roomType : RoomType) : Fridge{

        val fridge = Fridge(UUID.randomUUID(), eventHandler)
        house.rooms.first { room -> room.roomType == roomType }.addDevice(fridge)

        return fridge
    }

    /**
     * Whether the room exists, it creates a washer and puts it in
     * @param roomType where to put washer
     * @return created washer
     */
    fun addWasher(roomType : RoomType) : Washer{
        val washer = Washer(UUID.randomUUID(), eventHandler)
        house.rooms.first{room -> room.roomType == roomType}.addDevice(washer)

        return washer
    }


}