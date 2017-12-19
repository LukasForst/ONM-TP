package onm.human

import onm.events.EventHandler
import onm.events.HumanStopSport
import onm.events.IEventHandler
import onm.house.devices.AbstractDevice
import onm.house.devices.Dryer
import onm.things.Equipment
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.concurrent.thread

class NoSuchHumans : Exception()

class HumanControlUnit private constructor(availableHumans: Collection<Human>,
                                           private val eventHandler: IEventHandler) {

    private val _humansList = mutableListOf<Human>()
    val humans: Collection<Human>
        get() = _humansList

    private val _availableThings = mutableListOf<AbstractDevice>()
    val availableThings: Collection<AbstractDevice>
        get() = _availableThings

    private val _availableEquipment = ConcurrentLinkedQueue<Equipment>()
    val availableEquipment: Collection<Equipment>
        get() = _availableEquipment

    private val queueTodo = ConcurrentLinkedQueue<HumanTask>()
    private val queueWaitForHuman = ConcurrentLinkedQueue<HumanTask>()

    init {
        this._humansList.addAll(availableHumans)
        eventHandler.register(this)
    }

    companion object {
        val instance by lazy { HumanControlUnit(mutableListOf(), EventHandler.instance) }
    }


    private fun getAvailableHumanByAbility(ability: HumanAbility): Human? {
        return _humansList.firstOrNull { x -> x.abilitiesList.contains(ability) && x.available }

    }

    private fun getAvailableHuman(): Human? {
        return _humansList.firstOrNull { x -> x.available }
    }

    fun registerDevice(device: AbstractDevice) {
        _availableThings.add(device)
    }

    fun registerHuman(human: Human) {
        _humansList.add(human)
    }

    fun registerEquipment(equipment: Equipment) {
        _availableEquipment.add(equipment)
    }

    private fun start() {
        thread(start = true) {
            while (true) {
                if (queueTodo.isEmpty()) {
                    //If there are no stuff to do, go sport
                    //TODO: call this function before "if" if human is lenoch
                    chooseRandomSport()

                    if (queueWaitForHuman.isNotEmpty()) {
                        queueTodo.addAll(queueWaitForHuman)
                        queueWaitForHuman.clear()
                    }


                } else {
                    val task = queueTodo.poll()
                    when (task.type) {
                        TaskTypes.SHOP -> goShop(task)
                        TaskTypes.SPORT -> chooseRandomSport(task)
                        TaskTypes.REPAIR_DEVICE -> repairDevice(task)
                        TaskTypes.INTERACT_WITH_DEVICE -> interactWithDevice(task)
                    }
                }





                Thread.sleep(250)
            }
        }
    }

    private fun chooseRandomSport(task: HumanTask) {
        if (availableEquipment.isEmpty()) return

        try {
            val eq = _availableEquipment.poll()
            val h = getHumanByAbility(HumanAbility.SPORT_TYPE)
            h.doSport(eq, {
                HumanStopSport(eventHandler, h, h.id).raiseEvent()
                _availableEquipment.add(eq)
            })
        } catch (err: NoSuchHumans) {
            //TODO: no humans for sporting... Do nothing
        }
    }

    private fun goShop(task: HumanTask) {
        val h = getHumanByAbility(HumanAbility.CAN_COOK)
        h.goShop(device)

    }


    private fun repairDevice(task: HumanTask) {
        val h = getHumanByAbility(HumanAbility.CAN_REPAIR_DEVICES)
        h.repairDevice(device)
    }

    private fun interactWithDevice(task: HumanTask) {
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