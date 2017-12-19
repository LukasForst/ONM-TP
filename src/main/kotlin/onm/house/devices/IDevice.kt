package onm.house.devices

import onm.api.DataApi
import onm.interfaces.HouseEntity

interface IDevice : HouseEntity {

    /**
     * DataApi of this device to get consumption of energy
     */
    val dataApi: DataApi

    /**
     * Unique description of every device
     */
    val deviceDescription: String
}