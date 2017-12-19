package onm.api

import onm.house.devices.Toilet
import java.util.*

/**
 * Class representing api of a toilet. Toilet may be flushed.
 */
class ToiletControlApi(private val toilet: Toilet,
                       override val id: UUID) : IControlApi {

    /**
     * Simulates toilet flush. Whether toilet is turned off, new thread is created and keeps toilet
     * busy for (2x periodOfFlushing - flushing + pulling water)
     */
    fun flush() {
        toilet.flush()
    }


}