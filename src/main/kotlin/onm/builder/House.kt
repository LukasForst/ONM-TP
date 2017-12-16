package onm.builder

import onm.api.*
import onm.house.devices.Dryer
import onm.house.devices.Fridge
import onm.house.devices.Oven
import onm.house.devices.Washer
import onm.house.places.Room
import java.util.*
import kotlin.NoSuchElementException


/**
 * Class that holds all rooms in a house. //TODO
 */
//TODO add javadoc beacuse this works as API for now
class House internal constructor(){

    val rooms = LinkedList<Room>()

    val fridgeList = LinkedList<Fridge>()
    val washerList = LinkedList<Washer>()
    val ovenList = LinkedList<Oven>()
    val dryerList = LinkedList<Dryer>()


    fun getDataApi(deviceDescription: String): DataApi {
        for (room in rooms) {
            val device = room.devicesInRoom.firstOrNull { device -> device.deviceDescription.contentEquals(deviceDescription) }
            if (device != null) return device.dataApi
        }
        throw NoSuchElementException()
    }

    fun getFridgeControlApi(fridgeDescription: String): FridgeControlApi {
        val fridge = fridgeList.firstOrNull { fridge -> fridge.deviceDescription.contentEquals(fridgeDescription) }
        if (fridge != null) return fridge.fridgeControlApi
        throw NoSuchElementException()
    }

    fun getWasherControlApi(washerDescription: String): WasherControlApi {
        val washer = washerList.firstOrNull { washer -> washer.deviceDescription.contentEquals(washerDescription) }
        if (washer != null) return washer.washerControlApi
        throw NoSuchElementException()
    }

    fun getOvenControlApi(ovenDescription: String): OvenControlApi {
        val oven = ovenList.firstOrNull { oven -> oven.deviceDescription.contentEquals(ovenDescription) }
        if (oven != null) return oven.ovenControlApi
        throw NoSuchElementException()
    }

    fun getDryerControlApi(dryerDescription: String): DryerControlApi {
        val dryer = dryerList.firstOrNull { dryer -> dryer.deviceDescription.contentEquals(dryerDescription) }
        if (dryer != null) return dryer.dryerControlApi
        throw NoSuchElementException()
    }




}