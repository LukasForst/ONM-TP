package onm.builder

import onm.api.DataApi
import onm.api.IControlApi
import onm.house.Temperature
import onm.house.devices.IDevice
import onm.house.places.Room
import java.util.*
import kotlin.NoSuchElementException


/**
 * Class that holds all rooms in a house. //TODO
 */
//TODO add javadoc beacuse this works as API for now
class House internal constructor(){

    val inHouseTemperature = Temperature.instance

    internal val rooms = LinkedList<Room>()

    internal val allIDevices = LinkedList<IDevice>()
    val allIControlApi = LinkedList<IControlApi>()

    fun getUidOfDevice(deviceDescription: String): UUID {
        return allIDevices.firstOrNull { device -> device.deviceDescription.contentEquals(deviceDescription) }?.id ?: throw NoSuchElementException()
    }

    fun getDataApiByUUID(id: UUID): DataApi? {
        return allIDevices.singleOrNull { iDevice -> iDevice.id == id }?.dataApi
    }

    inline fun <reified T> getControlApiByUUID(id: UUID): T? {
        return allIControlApi.singleOrNull { iDevice -> iDevice.id == id }  as? T
    }
}