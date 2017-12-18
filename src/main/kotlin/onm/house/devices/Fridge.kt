package onm.house.devices

import onm.configuration.DeviceType
import onm.configuration.json.DeviceConfig
import onm.events.FridgeEmptyEvent
import onm.events.IEventHandler
import onm.reports.IReport
import onm.things.Food
import java.util.*
import kotlin.concurrent.thread

/**
 * Fridge representation.
 * */
class Fridge(override val id: UUID,
             eventHandler: IEventHandler,
             deviceConfig: DeviceConfig,
             private val workingIntervalInMinutes: Double = 1.0) : AbstractDevice(DeviceType.FRIDGE, deviceConfig, eventHandler) {

    private val fridgeEmptyEvent = FridgeEmptyEvent(eventHandler, id)
    private val _food = LinkedList<Food>()

    init { //todo what if electricity is turned off?
        //simulates fridge working

        thread(start = true) {
            while (true) {
                deviceStateMachine.idleState()
                Thread.sleep((workingIntervalInMinutes * 60000).toLong())
                deviceStateMachine.workingState()
                Thread.sleep((workingIntervalInMinutes * 60000).toLong())
            }
        }
    }

    /**
     * Get collection representing food in the fridge. When collection is empty FridgeEmptyEvent is raised.
     * */
    val food: Collection<Food>
        get() {
            if (_food.isEmpty()) {
                doWork(0, fridgeEmptyEvent::raiseEvent)
            }

            return _food
        }

    /**
     * Adds food in the fridge.
     * */
    fun addFood(food: Collection<Food>) {
        _food.addAll(food)
    }

    /**
     * Adds food in the fridge.
     * */
    fun addFood(food: Food) {
        _food.add(food)
    }


    override fun generateReport(): IReport {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}