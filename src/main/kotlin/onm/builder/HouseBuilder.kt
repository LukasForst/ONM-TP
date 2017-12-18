package onm.builder

import onm.configuration.DeviceType
import onm.configuration.json.ConfigurationDataClass
import onm.configuration.json.DeviceConfig
import onm.events.EventHandler
import onm.events.IEventHandler
import onm.house.devices.*
import onm.house.places.Room
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

        for (roomConfig in config.roomsAndDevices.keys) {
            val room = Room(UUID.randomUUID(), roomConfig.description ?: "No description provided.",
                    roomConfig.roomType, roomConfig.floor)

            for (deviceConfig in config.roomsAndDevices[roomConfig]!!) {
                room.addDevice(createDevice(deviceConfig, eventHandler))
            }
            house.rooms.add(room)
        }
        return house
    }

    private fun createDevice(deviceConfig: DeviceConfig, eventHandler: IEventHandler): AbstractDevice {
        val createdDevice = when (deviceConfig.deviceType) {
            DeviceType.WASHER -> Washer(UUID.randomUUID(), eventHandler, deviceConfig)
            DeviceType.FRIDGE -> Fridge(UUID.randomUUID(), eventHandler, deviceConfig)
            DeviceType.OVEN -> Oven(UUID.randomUUID(), eventHandler, deviceConfig)
            DeviceType.DRYER -> Dryer(UUID.randomUUID(), eventHandler, deviceConfig)
        }

        house.allIControlApi.add(when (deviceConfig.deviceType) {
            DeviceType.WASHER -> (createdDevice as Washer).washerControlApi
            DeviceType.FRIDGE -> (createdDevice as Fridge).fridgeControlApi
            DeviceType.OVEN -> (createdDevice as Oven).ovenControlApi
            DeviceType.DRYER -> (createdDevice as Dryer).dryerControlApi
        })
        house.allIDevices.add(createdDevice)
        return createdDevice
    }
}
