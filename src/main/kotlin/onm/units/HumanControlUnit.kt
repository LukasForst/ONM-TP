package onm.units

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

class NoSuchHumans : Exception();

class HumanControlUnit(private val humans: Collection<Human>) {
    init {
        for (h in humans) {
            h.controlUnit = this
        }
    }

    private val availableHumans = mutableListOf<Human>()
    private val availableEquipment = mutableListOf<Equipment>()
    private val availableThings = mutableListOf<AbstractDevice>()
    private val queueTodo = ConcurrentLinkedQueue<HumanTask>()
    private val queueWaitForHuman = ConcurrentLinkedQueue<HumanTask>()


    private fun getHumanByAbility(ability: HumanAbility): Human {
        val rest = availableHumans.filter { it.ability.compareTo(ability) > 0; it.available == true }.toList()
        if (rest.isNotEmpty()) {
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
                            goShop(task.device as Fridge)
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


                    if(queueWaitForHuman.isNotEmpty()){
                        queueTodo.addAll(queueWaitForHuman)
                        queueWaitForHuman.clear()
                    }

                }
            }

        }
    }

    private fun goShop(device: Fridge) {
        if (device != null) {
            val h = getHumanByAbility(HumanAbility.ANY)
            h.goShop(device)
        } else {
            //TODO: No device, log it
        }
    }


    private fun repairDevice(device: AbstractDevice) {
        val h = getHumanByAbility(HumanAbility.CAN_REPAIR_DEVICES)
        h.repairDevice(device);
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