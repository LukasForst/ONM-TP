package onm.builder

import onm.configuration.DeviceType
import onm.configuration.FurnitureType
import onm.configuration.RoomType
import onm.configuration.json.ConfigurationDataClass
import onm.configuration.json.DeviceConfig
import onm.configuration.json.RoomConfig
import onm.events.EventHandler
import onm.events.IEventHandler
import onm.house.devices.AbstractDevice
import onm.house.devices.Fridge
import onm.house.devices.Oven
import onm.house.devices.Washer
import onm.house.furniture.Furniture
import onm.house.places.Room
import onm.house.places.RoomBuilder
import java.util.*


/**
 * Class representing framework builder for devices and rooms in a smart house.
 */
class HouseBuilder private constructor(
        /**
         * Primary house reference.
         * */
        val house: House,

        /**
         * Event handler which will be used for devices created with config.
         * */
        private val eventHandler: IEventHandler) {

    companion object {
        /**
         * Builds empty house.
         * */
        fun createEmptyHouseBuilder(): HouseBuilder {
            val house = House()
            val eventHandler = EventHandler() //todo make it singleton
            return HouseBuilder(house, eventHandler)
        }

        /**
         * Builds house from given config class. This class should be parsed from JSON.
         * */
        fun createHouseBuilder(config: ConfigurationDataClass): HouseBuilder {
            val house = House()
            val eventHandler = EventHandler() //todo make event handler as singleton

            val houseBuilder = HouseBuilder(house, eventHandler)

            for (key in config.roomsAndDevices.keys) {
                val room = houseBuilder.addOrGetRoom(key)

                for (deviceConfig in config.roomsAndDevices[key]!!) {
                    room.addDevice(createDevice(deviceConfig, eventHandler))
                }
            }

            return houseBuilder
        }


        private fun createDevice(deviceConfig: DeviceConfig, eventHandler: IEventHandler): AbstractDevice {
            return when (deviceConfig.deviceType) {
                DeviceType.WASHER -> Washer(UUID.randomUUID(), eventHandler, deviceConfig.powerConsumption)
                DeviceType.FRIDGE -> Fridge(UUID.randomUUID(), eventHandler)
                DeviceType.OVEN -> Oven(UUID.randomUUID(), eventHandler)
            }
        }
    }

    /**
     * Adds or gets room in the house.
     */
    fun addOrGetRoom(roomConfig: RoomConfig): Room {
        var room = house.rooms.find { x -> x.roomType == roomConfig.roomType && x.floorNumber == roomConfig.floor && x.placeDescription == roomConfig.description }
        if (room != null) return room

        room = RoomBuilder(roomConfig).buildRoom()
        house.rooms.add(room)
        return room
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
