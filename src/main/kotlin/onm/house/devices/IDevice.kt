package onm.house.devices

import onm.api.DataApi
import onm.interfaces.StationaryEntity

interface IDevice : StationaryEntity {

    /**
     * DataApi of this device to get consumption of energy
     */
    val dataApi: DataApi

    /**
     * Unique description of every device
     */
    val deviceDescription: String
}