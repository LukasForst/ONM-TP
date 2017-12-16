package onm.api

import onm.house.devices.Washer

//TODO add javadoc beacuse this is API
class WasherControlApi(
        val washer: Washer) {


    fun startWashing(periodInMinutes: Double) {
        washer.startWashing(periodInMinutes)
    }
}