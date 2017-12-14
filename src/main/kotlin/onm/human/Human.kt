package onm.human

import onm.house.devices.Fridge
import onm.things.Food
import onm.things.FoodType
import onm.units.HumanControlUnit
import kotlin.concurrent.thread

class Human(val ability: HumanAbility) {
    var available: Boolean = true

    var controlUnit: HumanControlUnit? = null

    fun goShop(frigo: Fridge) {
        available = false;
        thread(start = true) {
            Thread.sleep(10000)
            // Generate food
            val types = FoodType.values()

            val ret = types.map { Food(it) }

            frigo.addFood(ret)
            available = true;
        }
    }
}