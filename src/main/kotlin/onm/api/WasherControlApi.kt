package onm.api

import onm.house.devices.Washer
import java.util.*

/**
 * Class representing api of washer. Washer may wash clothes for x minutes.
 */
class WasherControlApi(
        private val washer: Washer, override val id: UUID) : IControlApi {


    /**
     * Washer starts washing for input minutes
     * @param periodInMinutes time period of washing in minutes
     */
    fun startWashing(periodInMinutes: Double) {
        washer.startWashing(periodInMinutes)
    }
}