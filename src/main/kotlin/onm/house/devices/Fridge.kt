package onm.house.devices

import onm.api.FridgeControlApi
import onm.configuration.DeviceType
import onm.configuration.json.DeviceConfig
import onm.events.DeviceStartsEvent
import onm.events.DeviceTurnedOffEvent
import onm.events.FridgeEmptyEvent
import onm.events.IEventHandler
import onm.house.places.Room
import onm.human.HumanControlUnit
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
             room: Room,
             private val workingIntervalInMinutes: Double = 1.0) : AbstractDevice(DeviceType.FRIDGE, deviceConfig, eventHandler, room) {

    private val fridgeEmptyEvent = FridgeEmptyEvent(eventHandler, id, this)
    private val fridgeStartsEvent = DeviceStartsEvent(eventHandler, id, "Fridge named $deviceDescription is turned on.")
    private val fridgeTurnedOffEvent = DeviceTurnedOffEvent(eventHandler, id, "Fridge $deviceDescription is turned off.")

    private val _food = LinkedList<Food>()

    val fridgeControlApi = FridgeControlApi(this, this.id)

    init { //todo what if electricity is turned off?
        //simulates fridge working
        HumanControlUnit.instance.registerDevice(this)
        switchOn()
    }

    fun switchOn() {
        if (deviceStateMachine.currentState.stateType != StateType.TURNED_OFF) {
            log.error("Fridge named '$deviceDescription' is not turned off, therefore cannot be turned on.")
            return
        }

        fridgeStartsEvent.raiseEvent()
        thread(start = true) {
            while (true) {
                doWork((workingIntervalInMinutes * 60000).toLong(), null)
                if (isBrokenOrOff()) return@thread

                Thread.sleep((workingIntervalInMinutes * 60000).toLong()) //Simulates resting fridge
                if (isBrokenOrOff()) return@thread
            }
        }
    }


    fun switchOff() {
        if (deviceStateMachine.currentState.stateType == StateType.BROKEN)
            log.error("Fridge named '$deviceDescription is broken, cannot be turned off")
        else {
            deviceStateMachine.turnedOffState()
            fridgeTurnedOffEvent.raiseEvent()
        }
    }

    private fun isOff(): Boolean {
        return deviceStateMachine.currentState.stateType == StateType.TURNED_OFF
    }

    private fun isBrokenOrOff(): Boolean {
        return deviceStateMachine.currentState.stateType == StateType.BROKEN || isOff()
    }


    /**
     * Get collection representing food in the fridge. When collection is empty FridgeEmptyEvent is raised and empty list is returned.
     * */
    val food: LinkedList<Food>
        get() {
            if (_food.isEmpty()) {
                fridgeEmptyEvent.raiseEvent()
            }

            return _food
        }

    fun getListOfFood(): LinkedList<Food> {
        return _food
    }

    override fun generateReport(): IReport {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}