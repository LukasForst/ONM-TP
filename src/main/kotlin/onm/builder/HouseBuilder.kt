package onm.builder

import onm.configuration.DeviceType
import onm.configuration.json.ConfigurationDataClass
import onm.configuration.json.DeviceConfig
import onm.configuration.json.RoomConfig
import onm.events.EventHandler
import onm.events.IEventHandler
import onm.house.devices.*
import onm.house.places.Room
import onm.house.places.RoomBuilder
import java.util.*


/**
 * Class representing framework builder for devices and rooms in a smart house.
 */
object HouseBuilder {

    private val house = House()
    /**
     * Builds house from given config class. This class should be parsed from JSON.
     * */
    fun buildHouseFromConfig(config: ConfigurationDataClass): House {
        val eventHandler = EventHandler.instance

        for (roomConfig in config.rooms) {
            val room = createRoom(roomConfig, eventHandler)
            roomConfig.devices.forEach {
                createDevice(it, eventHandler, room)

            }
            house.rooms.add(room)

        }
        for( i in config.vehicles){

        }
        return house
    }

    private fun createDevice(deviceConfig: DeviceConfig, eventHandler: IEventHandler, room: Room): AbstractDevice {
        val createdDevice = when (deviceConfig.type) {
            DeviceType.WASHER -> Washer(UUID.randomUUID(), eventHandler, deviceConfig, room)
            DeviceType.FRIDGE -> Fridge(UUID.randomUUID(), eventHandler, deviceConfig, room)
            DeviceType.OVEN -> Oven(UUID.randomUUID(), eventHandler, deviceConfig, room)
            DeviceType.DRYER -> Dryer(UUID.randomUUID(), eventHandler, deviceConfig, room)
            DeviceType.TELEVISION -> Television(UUID.randomUUID(), eventHandler, deviceConfig, room)
        }

        house.allIControlApi.add(when (deviceConfig.type) {
            DeviceType.TELEVISION -> (createdDevice as Television).televisionControlApi
            DeviceType.WASHER -> (createdDevice as Washer).washerControlApi
            DeviceType.FRIDGE -> (createdDevice as Fridge).fridgeControlApi
            DeviceType.OVEN -> (createdDevice as Oven).ovenControlApi
            DeviceType.DRYER -> (createdDevice as Dryer).dryerControlApi
        })
        house.allIDevices.add(createdDevice)
        return createdDevice
    }

    private fun createRoom(room: RoomConfig, eventHandler: IEventHandler): Room {
        val builder = RoomBuilder(room)
        return builder.buildRoom()
    }
}

