package onm.units

import onm.events.BakeFinishedEvent
import onm.events.FridgeEmptyEvent
import onm.events.IEvent
import onm.house.devices.AbstractDevice
import onm.human.Human
import onm.human.HumanAbility
import onm.things.Equipment
import java.util.concurrent.*
import kotlin.concurrent.thread

class NoSuchHumans : Exception();

class HumanControlUnit(private val humans: Collection<Human>) {
    private val availableHumans = ArrayList<Human>()
    private val availableEquipment = ArrayList<Equipment>()
    private val availableThings = ArrayList<AbstractDevice>()
    private val queueTodo = ConcurrentLinkedQueue<IEvent>()
    private val queueWaitForHuman = ConcurrentLinkedQueue<IEvent>()


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
                val event = queueTodo.poll()
                when {
                    event is FridgeEmptyEvent -> {
                        try {
                            val h = getHumanByAbility(HumanAbility.CAN_COOK)

                        } catch (err: NoSuchHumans) {
                            queueWaitForHuman.add(event)
                        }
                    }
                    event is BakeFinishedEvent -> {

                    }
                }

            }
        }
    }

    fun handleEvent(event: IEvent) {
        queueTodo.add(event)
        if (queueTodo.size == 1) {
            start()
        }
    }


}