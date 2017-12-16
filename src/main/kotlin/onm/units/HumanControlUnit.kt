package onm.units

import onm.api.FridgeControlApi
import onm.events.EventHandler
import onm.events.HumanStopSport
import onm.house.devices.AbstractDevice
import onm.house.devices.Dryer
import onm.house.devices.Fridge
import onm.human.Human
import onm.human.HumanAbility
import onm.human.HumanTask
import onm.human.TaskTypes
import onm.things.Equipment
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.concurrent.thread

class NoSuchHumans : Exception()

class HumanControlUnit(private val availableHumans: Collection<Human>,
                       protected val eventHandler: EventHandler) {

    private val availableEquipment = ConcurrentLinkedQueue<Equipment>()
    private val availableThings = mutableListOf<AbstractDevice>()
    private val queueTodo = ConcurrentLinkedQueue<HumanTask>()
    private val queueWaitForHuman = ConcurrentLinkedQueue<HumanTask>()


    private fun getHumanByAbility(ability: HumanAbility): Human {
        val rest = availableHumans.filter { it.ability.compareTo(ability) > 0; it.available == true }.toList()
        if (rest.isNotEmpty()) {
            return rest[0]
        } else {
            throw NoSuchHumans()
        }
    }


    private fun start() {
        thread(start = true) {
            while (!queueTodo.isEmpty()) {
                val task = queueTodo.poll()
                when (task.type) {
                    TaskTypes.SHOP -> {
                        try {
                            goShop((task.device as Fridge).fridgeControlApi)
                        } catch (err: NoSuchHumans) {
                            queueWaitForHuman.add(task)
                        }
                    }

                    TaskTypes.INTERACT_WITH_DEVICE -> {
                        try {
                            interactWithDevice(task.device!!)
                        } catch (err: NoSuchHumans) {
                            queueWaitForHuman.add(task)
                        }
                    }
                    TaskTypes.REPAIR_DEVICE -> {
                        try {
                            repairDevice(task.device!!)
                        } catch (err: NoSuchHumans) {
                            queueWaitForHuman.add(task)
                        }
                    }
                }

                // Add all stuff to queue when it possible
                if (queueTodo.isEmpty()) {
                    //If there are no stuff to do, go sport
                    //TODO: call this function before "if" if human is lenoch
                    choseRandomSport()

                    if (queueWaitForHuman.isNotEmpty()) {
                        queueTodo.addAll(queueWaitForHuman)
                        queueWaitForHuman.clear()
                    }

                }
            }

        }
    }

    private fun choseRandomSport() {
        if (availableEquipment.isEmpty()) return

        try {
            val eq = availableEquipment.poll()
            val h = getHumanByAbility(HumanAbility.ANY)
            h.doSport(eq, {
                HumanStopSport(eventHandler, h).raiseEvent()
                availableEquipment.add(eq)
            })
        } catch (err: NoSuchHumans) {
            //TODO: no humans for sporting... Do nothing
        }
    }

    private fun goShop(device: FridgeControlApi) {
        val h = getHumanByAbility(HumanAbility.ANY)
        h.goShop(device)

    }


    private fun repairDevice(device: AbstractDevice) {
        val h = getHumanByAbility(HumanAbility.CAN_REPAIR_DEVICES)
        h.repairDevice(device)
    }

    private fun interactWithDevice(device: AbstractDevice) {
        when (device) {
            is Dryer -> {
                val h = getHumanByAbility(HumanAbility.ANY)
                h.dryingClothes(device)
            }
        //TODO: implement other actions
        }
    }

    fun handleTask(task: HumanTask) {
        queueTodo.add(task)
        if (queueTodo.size == 1) {
            start()
        }
    }


}