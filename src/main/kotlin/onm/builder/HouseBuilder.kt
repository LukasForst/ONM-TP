package onm.builder

import onm.animals.*
import onm.configuration.DeviceType
import onm.configuration.json.*
import onm.events.EventHandler
import onm.events.IEventHandler
import onm.house.devices.*
import onm.house.places.Room
import onm.house.places.RoomBuilder
import onm.human.Human
import onm.human.HumanControlUnit
import java.util.*


/**
 * Class representing framework builder for devices and rooms in a smart house.
 */
object HouseBuilder {

    private var house = House()

    private val eventHandler = EventHandler.instance
    private val humanControl = HumanControlUnit.instance
    private val animalControl = AnimalControlUnit.instance

    /**
     * Builds house from given config class. This class should be parsed from JSON.
     * */
    fun buildHouseFromConfig(config: ConfigurationDataClass): House {
        createAndFillRooms(config.rooms)
        createAndAddEquipment(config.equipments)
        populateHouseWithAnimals(config.animals)
        populateHouseWithHumans(config.humans)
        return build()
    }

    /**
     * Creates House from config you have provided.
     *
     * Note that after calling this method will this instance has again empty house.
     * */
    fun build(): House {
        val result = house
        house = House()
        return result
    }

    fun createAndAddEquipment(equipmentConfig: Collection<EquipmentConfig>): HouseBuilder {
        equipmentConfig.forEach { x -> humanControl.registerEquipment(x.type) }
        return this
    }

    fun populateHouseWithAnimals(animalConfig: Collection<AnimalConfig>): HouseBuilder {
        animalConfig.forEach { x -> createAnimal(x) }
        return this
    }

    fun createAndFillRooms(rooms: Collection<RoomConfig>): HouseBuilder {
        for (roomConfig in rooms) {
            val room = createRoom(roomConfig)
            roomConfig.devices.forEach { x -> createDevice(x, eventHandler, room) }
            house.rooms.add(room)
        }
        return this
    }

    fun populateHouseWithHumans(humans: Collection<HumansConfig>): HouseBuilder {
        humans.forEach { x -> Human(x.humanAbility, x.name, humanControl, UUID.randomUUID()) }
        return this
    }

    private fun createAnimal(config: AnimalConfig): IAnimal {
        return when (config.type) {
            AnimalType.CAT -> Cat(UUID.randomUUID(), config.name, animalControl)
            AnimalType.DOG -> Dog(UUID.randomUUID(), config.name, animalControl)
            AnimalType.GOLF_FISH -> GoldFish(UUID.randomUUID(), config.name, animalControl)
            AnimalType.SPIDER -> Spider(UUID.randomUUID(), config.name, animalControl)
            AnimalType.SNAKE -> Snake(UUID.randomUUID(), config.name, animalControl)
        }
    }

    private fun createDevice(deviceConfig: DeviceConfig, eventHandler: IEventHandler, room: Room): AbstractDevice {
        val createdDevice = when (deviceConfig.type) {
            DeviceType.WASHER -> Washer(UUID.randomUUID(), eventHandler, deviceConfig, room)
            DeviceType.FRIDGE -> Fridge(UUID.randomUUID(), eventHandler, deviceConfig, room)
            DeviceType.OVEN -> Oven(UUID.randomUUID(), eventHandler, deviceConfig, room)
            DeviceType.DRYER -> Dryer(UUID.randomUUID(), eventHandler, deviceConfig, room)
            DeviceType.TELEVISION -> Television(UUID.randomUUID(), eventHandler, deviceConfig, room)
            DeviceType.CAR -> Car(UUID.randomUUID(), deviceConfig, eventHandler, room)
            DeviceType.RADIO -> Radio(UUID.randomUUID(), eventHandler, deviceConfig, room)
            DeviceType.TOILET -> Toilet(UUID.randomUUID(), eventHandler, deviceConfig, room)
            DeviceType.BOILER -> Boiler(UUID.randomUUID(), deviceConfig, eventHandler, room, 25) //todo maybe read thresh hold from somewhere
        }

        house.allIControlApi.add(when (deviceConfig.type) {
            DeviceType.TELEVISION -> (createdDevice as Television).televisionControlApi
            DeviceType.WASHER -> (createdDevice as Washer).washerControlApi
            DeviceType.FRIDGE -> (createdDevice as Fridge).fridgeControlApi
            DeviceType.OVEN -> (createdDevice as Oven).ovenControlApi
            DeviceType.DRYER -> (createdDevice as Dryer).dryerControlApi
            DeviceType.CAR -> (createdDevice as Car).carControlApi
            DeviceType.RADIO -> (createdDevice as Radio).radioControlApi
            DeviceType.TOILET -> (createdDevice as Toilet).toiletControlApi
            DeviceType.BOILER -> (createdDevice as Boiler).controlApi
        })
        house.allIDevices.add(createdDevice)
        return createdDevice
    }

    private fun createRoom(room: RoomConfig): Room {
        return RoomBuilder(room).buildRoom()
    }
}

