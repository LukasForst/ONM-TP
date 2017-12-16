package onm.builder

import onm.api.DataApi
import onm.api.IControlApi
import onm.house.devices.IDevice
import onm.house.places.Room
import java.util.*
import kotlin.NoSuchElementException


/**
 * Class that holds all rooms in a house. //TODO
 */
//TODO add javadoc beacuse this works as API for now
class House internal constructor(){

    internal val rooms = LinkedList<Room>()

    internal val allIDevices = LinkedList<IDevice>()
    val allIControlApi = LinkedList<IControlApi>()

    fun getUidOfDevice(deviceDescription: String): UUID {
        val id = allIDevices.firstOrNull { device -> device.deviceDescription.contentEquals(deviceDescription) }?.id ?: throw NoSuchElementException()
        return id
    }

    fun getDataApiByUUID(id: UUID): DataApi? {
        return allIDevices.singleOrNull { iDevice -> iDevice.id == id }?.dataApi
    }

    inline fun <reified T> getControlApiByUUID(id: UUID): T? {
        val controlApi = allIControlApi.singleOrNull { iDevice -> iDevice.id == id } ?: return null
        return controlApi as? T
    }
}