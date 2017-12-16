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

    val house = House()
    /**
     * Builds house from given config class. This class should be parsed from JSON.
     * */
    fun buildHouseFromConfig(config: ConfigurationDataClass): House {
        val eventHandler = EventHandler() //todo make event handler as singleton

        for (roomConfig in config.roomsAndDevices.keys) {
            val room = Room(UUID.randomUUID(), roomConfig.description ?: "No description provided.",
                    roomConfig.roomType, roomConfig.floor)

            for (deviceConfig in config.roomsAndDevices[roomConfig]!!) {
                room.addDevice(createDevice(deviceConfig, eventHandler))
            }
        }

        return house
    }

    private fun createDevice(deviceConfig: DeviceConfig, eventHandler: IEventHandler): AbstractDevice {
        when (deviceConfig.deviceType) {
            DeviceType.WASHER -> {
                val washer = Washer(UUID.randomUUID(), eventHandler, deviceConfig)
                house.washerList.add(washer)
                return washer
            }
            DeviceType.FRIDGE -> {
                val fridge = Fridge(UUID.randomUUID(), eventHandler, deviceConfig)
                house.fridgeList.add(fridge)
                return fridge
            }
            DeviceType.OVEN -> {
                val oven = Oven(UUID.randomUUID(), eventHandler, deviceConfig)
                house.ovenList.add(oven)
                return oven
            }
            DeviceType.DRYER -> {
                val dryer = Dryer(UUID.randomUUID(), eventHandler, deviceConfig)
                house.dryerList.add(dryer)
                return dryer
            }
        }
    }
}
