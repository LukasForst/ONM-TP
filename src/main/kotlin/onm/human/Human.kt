package onm.human

import onm.api.FridgeControlApi
import onm.configuration.EventSeverity
import onm.events.HumanDoSport
import onm.events.IEventHandler
import onm.house.devices.AbstractDevice
import onm.interfaces.HouseEntity
import onm.reports.HumanReport
import onm.reports.IReport
import onm.things.Equipment
import onm.things.Food
import onm.things.FoodType
import java.time.Instant
import java.util.*
import kotlin.concurrent.thread

class Human(val abilitiesList: Collection<HumanAbility>, val name: String, private val controlUnit: HumanControlUnit, override val id: UUID) : HouseEntity {
    internal var available: Boolean = true

    init {
        controlUnit.registerHuman(this)
    }

    override fun generateReport(): IReport {
        val status = if (available) "busy"; else "free"
        return HumanReport(Instant.now(), id, "Human $name is $status", EventSeverity.INFO, this)
    }


    /**
     * Human simulates shopping. New thread is created and sleeps for 15 seconds.
     * Fridge is then filled with new food of every known type.
     */
    fun goShop(fridgeApi: FridgeControlApi) {
        available = false
        thread(start = true) {
            Thread.sleep(10000)
            // Generate food
            val types = FoodType.values()
            val ret = types.map { Food(it) }

            fridgeApi.addFood(ret)
            available = true
        }
    }

    /**
     * Human simulates repairing device.
     * New thread is created and sleeps for 10 seconds.
     * State of broken device given in parameter is then changed to TurnedOff.
     */
    fun repairDevice(device: AbstractDevice) {
        available = false
        thread(start = true) {
            //Simulate work
            Thread.sleep(10000)
            device.repair()
            available = true
        }
    }

    /**
     * Human simulates sport. New thread is created and sleeps for 20 seconds. Callback is then called
     */
    fun doSport(eventHandler: IEventHandler, equipment: Equipment, callback: () -> Unit) {
        available = false

        thread(start = true) {
            HumanDoSport(eventHandler, this, id).raiseEvent()

            Thread.sleep(20000)
            callback.invoke()

            controlUnit._availableEquipment.add(equipment)
            available = true
        }
    }
}