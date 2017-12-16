package onm.api

import onm.house.devices.Oven
import onm.things.Food
import java.util.*


/**
 * Class representing api of oven. Oven may bake input food for x minutes.
 */
class OvenControlApi(
        private val oven: Oven, override val id: UUID) : IControlApi {

    /**
     * Oven starts baking for input minutes
     * @param food food to be baked in oven
     * @param minutes time period of baking in minutes
     */
    fun switchOn(food: Collection<Food>, minutes: Double) {
        oven.switchOn(food, minutes)
    }
}