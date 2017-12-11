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
     * If there is not a room with given type and description, the room is created and added to the house
     * @param type Type of desired room
     * @param description description/name of desired room
     */
    fun addRoom(type: RoomType, description: String) {

        if (!house.rooms.any { room -> room.roomType == type && room.placeDescription == description })
            house.rooms.add(RoomBuilder(type, description).buildRoom())
        else {
            //TODO log bad usage of API (or throw exception??)
        }
    }

    /**
     * Whether the room exists, it creates a fridge and puts it in
     * @param roomType type of a room where to put fridge
     * @param roomDescription name of a room where to put fridge
     * @return created fridge
     */
    fun addFridge(roomType: RoomType, roomDescription: String): Fridge {

        val room = house.rooms.find { room -> room.roomType == roomType && room.placeDescription == roomDescription }
        if (room == null) throw NoSuchFieldException() //TODO maybe throw better exception
        else {

            val fridge = Fridge(UUID.randomUUID(), eventHandler)
            room.addDevice(fridge)
            return fridge
        }
    }

    /**
     * Whether the room exists, it creates a washer and puts it in
     * @param roomType where to put washer
     * @param roomDescription name of a room where to put washer
     * @return created washer
     */
    fun addWasher(roomType: RoomType, roomDescription: String): Washer {

        val room = house.rooms.find { room -> room.roomType == roomType && room.placeDescription == roomDescription }
        if (room == null) throw NoSuchFieldException() //TODO maybe throw better exception
        else {

            val washer = Washer(UUID.randomUUID(), eventHandler)
            room.addDevice(washer)
            return washer
        }
    }

    /**
     * Whether the room exists, it creates a washer and puts it in
     * @param roomType where to put washer
     * @param roomDescription name of a room where to put oven
     * @return created oven
     */
    fun addOven(roomType: RoomType, roomDescription: String): Oven {
        val room = house.rooms.find { room -> room.roomType == roomType && room.placeDescription == roomDescription }
        if (room == null) throw NoSuchFieldException() //TODO maybe throw better exception
        else {

            val oven = Oven(UUID.randomUUID(), eventHandler)
            room.addDevice(oven)
            return oven
        }
    }


    /**
     * Creates desired furniture in a given room.
     * @param roomType where to put new furniture
     * @param furnitureType type of furniture to be created
     * @param roomDescription name of a room where to put new furniture
     */
    fun addFurniture(roomType: RoomType, furnitureType: FurnitureType, roomDescription: String) {
        val room = house.rooms.find { room -> room.roomType == roomType && room.placeDescription == roomDescription }
        if (room == null) throw NoSuchFieldException() //TODO maybe throw better exception
        else {
            val furniture = Furniture(UUID.randomUUID(), furnitureType)
            room.addFurniture(furniture)
        }
    }
}
