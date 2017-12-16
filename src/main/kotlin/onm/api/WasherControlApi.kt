package onm.api

import onm.house.devices.Washer

/**
 * Class representing api of washer. Washer may wash clothes for x minutes.
 */
class WasherControlApi(
        val washer: Washer) {


    /**
     * Washer starts washing for input minutes
     * @param periodInMinutes time period of washing in minutes
     */
    fun startWashing(periodInMinutes: Double) {
        washer.startWashing(periodInMinutes)
    }
}