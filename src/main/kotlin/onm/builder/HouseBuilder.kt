package onm.builder

import onm.configuration.FurnitureType
import onm.configuration.RoomType
import onm.events.EventHandler
import onm.house.devices.Fridge
import onm.house.devices.Oven
import onm.house.devices.Washer
import onm.house.furniture.Furniture
import onm.house.places.RoomBuilder
import java.util.*

/**
 * Class representing framework builder for devices and rooms in a smart house.
 */
class HouseBuilder {


    /**
     * Event handler dealing with all raised events from i.e., devices
     */
    val eventHandler = EventHandler() //TODO later add via constructor and implement wrapper

    /**
     * House variable containing references to all rooms
     */
    val house = House() //TODO later add via constructor and implement wrapper

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

    /**
     * Whether the room exists, it creates a washer and puts it in
     * @param roomType where to put washer
     * @return created oven
     */
    fun addOven(roomType: RoomType): Oven {
        val oven = Oven(UUID.randomUUID(), eventHandler)
        house.rooms.first { room -> room.roomType == roomType }.addDevice(oven)

        return oven
    }


    /**
     * Creates desired furniture in a given room.
     * @param roomType where to put new furniture
     * @param furnitureType type of furniture to be created
     */
    fun addFurniture(roomType: RoomType, furnitureType: FurnitureType) {
        val furniture = Furniture(UUID.randomUUID(), furnitureType)
        house.rooms.first { room -> room.roomType == roomType }.addFurniture(furniture)
    }
}
