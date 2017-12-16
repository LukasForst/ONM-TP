package onm.api

import onm.house.devices.Oven
import onm.things.Food


//TODO add javadoc beacuse this is API
class OvenControlApi(
        val oven: Oven) {

    fun switchOn(food: Collection<Food>, minutes: Double) {
        oven.switchOn(food, minutes)
    }
}