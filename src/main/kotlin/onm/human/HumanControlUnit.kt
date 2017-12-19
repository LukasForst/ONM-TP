package onm.human

import onm.events.EventHandler
import onm.events.HumanStopSport
import onm.events.IEventHandler
import onm.house.devices.AbstractDevice
import onm.house.devices.Fridge
import onm.loggerFor
import onm.things.Equipment
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.concurrent.thread


class HumanControlUnit private constructor(availableHumans: Collection<Human>,
                                           private val eventHandler: IEventHandler) {

    private val _humansList = ConcurrentLinkedQueue<Human>()
    val humans: Collection<Human>
        get() = _humansList

    private val _availableThings = ConcurrentLinkedQueue<AbstractDevice>()
    val availableThings: Collection<AbstractDevice>
        get() = _availableThings

    val availableEquipment = ConcurrentLinkedQueue<Equipment>()

    val queueTodo = ConcurrentLinkedQueue<HumanTask>()

    init {
        this._humansList.addAll(availableHumans)
        eventHandler.register(this)
        start()
    }

    companion object {
        val instance by lazy { HumanControlUnit(mutableListOf(), EventHandler.instance) }
        val log = loggerFor(HumanControlUnit::class.java)
    }


    private fun getAvailableHumanByAbility(ability: HumanAbility): Human? {
        return _humansList.firstOrNull { x -> x.abilitiesList.contains(ability) && x.available }
    }


    fun registerDevice(device: AbstractDevice) {
        _availableThings.add(device)
    }

    fun registerHuman(human: Human) {
        _humansList.add(human)
    }

    fun registerEquipment(equipment: Equipment) {
        availableEquipment.add(equipment)
    }

    private fun start() {
        thread(start = true) {
            while (true) {
                if (queueTodo.isEmpty()) {
                    //If there are no stuff to do, go sport
                    createSportTask()

                } else {
                    val task = queueTodo.poll()
                    when (task.type) {
                        TaskTypes.SHOP -> goShop(task)
                        TaskTypes.SPORT -> doSport(task)
                        TaskTypes.REPAIR_DEVICE -> repairDevice(task)
                    }
                }
                Thread.sleep(250)
            }
        }
    }

    private fun createSportTask() {
        val task = HumanTask(TaskTypes.SPORT)
        queueTodo.add(task)
    }

    private fun doSport(task: HumanTask) {
        if (availableEquipment.isEmpty()) {
            queueTodo.add(task)
            return
        }

        val human = getAvailableHumanByAbility(HumanAbility.SPORT_TYPE)
        val equip = availableEquipment.poll()
        human?.doSport(eventHandler, equip, HumanStopSport(eventHandler, human, human.id)::raiseEvent)
                ?: {
            queueTodo.add(task)
            availableEquipment.add(equip)
        }.invoke()
    }

    private fun goShop(task: HumanTask) {
        val human = getAvailableHumanByAbility(HumanAbility.CAN_COOK)
        val fridge = ((task.device) as? Fridge)?.fridgeControlApi

        if (fridge != null && human != null) human.goShop(fridge)
        else log.error("ERROR HumanControlUnit! Fridge or human not found in goShop!!")

    }


    private fun repairDevice(task: HumanTask) {
        val human = getAvailableHumanByAbility(HumanAbility.CAN_REPAIR_DEVICES)
        val device = task.device

        when {
            device == null -> log.error("ERROR HumanControlUnit! Device not found in repairDevice!")
            human == null -> {
                queueTodo.add(task)
                log.warn("There was no human with needed ability: ${HumanAbility.CAN_REPAIR_DEVICES}")
            }
            else -> human.repairDevice(device)
        }
    }
}