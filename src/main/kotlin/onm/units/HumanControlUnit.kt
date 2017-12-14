package onm.units

import onm.house.devices.AbstractDevice
import onm.house.devices.Fridge
import onm.human.Human
import onm.human.HumanAbility
import onm.human.HumanTask
import onm.human.TaskTypes
import onm.things.Equipment
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.concurrent.thread

class NoSuchHumans : Exception();

class HumanControlUnit(private val humans: Collection<Human>) {
    init {
        for (h in humans) {
            h.controlUnit = this
        }
    }

    private val availableHumans = ArrayList<Human>()
    private val availableEquipment = ArrayList<Equipment>()
    private val availableThings = mutableListOf<AbstractDevice>()
    private val queueTodo = ConcurrentLinkedQueue<HumanTask>()
    private val queueWaitForHuman = ConcurrentLinkedQueue<HumanTask>()


    private fun getHumanByAbility(ability: HumanAbility): Human {
        val rest = availableHumans.filter { it.ability.compareTo(ability) > 0; it.available == true }.toList()
        if (rest.size > 0) {
            return rest[0];
        } else {
            throw NoSuchHumans();
        }
    }


    private fun start() {
        thread(start = true) {
            while (!queueTodo.isEmpty()) {
                val task = queueTodo.poll()
                when (task.type) {
                    TaskTypes.SHOP -> {
                        try {
                            if (task.device != null) {
                                val h = getHumanByAbility(HumanAbility.ANY)
                                h.goShop(task.device as Fridge)
                            } else {
                                //TODO: No device, log it
                            }

                        } catch (err: NoSuchHumans) {
                            queueWaitForHuman.add(task)
                        }
                    }
                }
            }
        }
    }

    fun handleTask(task: HumanTask) {
        queueTodo.add(task)
        if (queueTodo.size == 1) {
            start()
        }
    }


}