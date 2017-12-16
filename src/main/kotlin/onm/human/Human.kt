package onm.human

import onm.house.devices.AbstractDevice
import onm.house.devices.Dryer
import onm.house.devices.Fridge
import onm.things.Equipment
import onm.things.Food
import onm.things.FoodType
import onm.units.HumanControlUnit
import kotlin.concurrent.thread

class Human(val ability: HumanAbility, val name: String, val controlUnit: HumanControlUnit) {
    var available: Boolean = true

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

    fun dryingClothes(dryer: Dryer) {
        available = false
        thread(start = true) {
            //Simulate work
            Thread.sleep(1000)
            dryer.switchOn()
            available = true;
        }
    }

    fun repairDevice(device: AbstractDevice) {
        available = false
        thread(start = true) {
            //Simulate work
            Thread.sleep(2000)
            device.repair()
            available = true;
        }
    }

    fun doSport(equipment: Equipment, callback: () -> Unit) {
        available = false
        thread(start = true) {
            Thread.sleep(4000)

            available = true
            callback.invoke()
        }
    }
}