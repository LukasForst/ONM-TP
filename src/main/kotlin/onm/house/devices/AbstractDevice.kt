package onm.house.devices

import onm.configuration.DeviceType
import onm.house.places.Room
import onm.interfaces.StationaryEntity

/**
 * Abstract device class represents device in the house. All future devices should implements this class.
 * */
abstract class AbstractDevice(
        /**
         * Type of device.
         * */
        val deviceType: DeviceType) : StationaryEntity {


    //todo get specs and implement this


    /**
     * Room reference. This should be set after adding device to the room.
     * */
    var room: Room? = null

    /**
     * Determines state of device - whether is working or not.
     * */
    var isBusy: Boolean = false
        @Synchronized get() = field
        @Synchronized set(value) {
            field = value
        }


}