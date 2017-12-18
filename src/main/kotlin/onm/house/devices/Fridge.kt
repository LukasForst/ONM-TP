package onm.house.devices

import onm.api.FridgeControlApi
import onm.configuration.DeviceType
import onm.configuration.json.DeviceConfig
import onm.events.FridgeEmptyEvent
import onm.events.IEventHandler
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

    private val fridgeEmptyEvent = FridgeEmptyEvent(eventHandler)
    private val _food = LinkedList<Food>()

    val fridgeControlApi = FridgeControlApi(this, this.id)

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
    val food: LinkedList<Food>
        get() {
            if (_food.isEmpty()) {
                doWork(0, fridgeEmptyEvent::raiseEvent)
            }

            return _food
        }

    fun getListOfFood(): LinkedList<Food> {
        return _food
    }

    override fun generateReport(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}