package onm.house.devices

import onm.api.WasherControlApi
import onm.configuration.DeviceType
import onm.configuration.json.DeviceConfig
import onm.events.DeviceFinishedEvent
import onm.events.DeviceStartsEvent
import onm.events.DeviceTurnedOffEvent
import onm.events.IEventHandler
import onm.house.places.Room
import onm.human.HumanControlUnit
import onm.reports.IReport
import java.util.*
import kotlin.concurrent.thread

/**
 * Wash machine representation
 * */
class Washer(override val id: UUID,
             eventHandler: IEventHandler,
             deviceConfig: DeviceConfig,
             room: Room) : AbstractDevice(DeviceType.WASHER, deviceConfig, eventHandler, room) {

    init {
        HumanControlUnit.instance.registerDevice(this)
    }

    private val washerFinishedEvent = DeviceFinishedEvent(eventHandler, id, "Washing clothes using $deviceDescription is done.")
    private val washerStartsEvent = DeviceStartsEvent(eventHandler, id, "Washer named $deviceDescription is turned on.")
    private val washerTurnedOffEvent = DeviceTurnedOffEvent(eventHandler, id, "Washer $deviceDescription is turned off.")

    val washerControlApi = WasherControlApi(this, id)

    /**
     * Starts washing clothes. This produces event which is raised after given time period. Note that new thread is created.
     * */
    fun startWashing(periodInMinutes: Double) {
        if (deviceStateMachine.currentState.stateType != StateType.TURNED_OFF)
            log.error("Washer named '$deviceDescription' is not turnedOff, therefore cannot be switched on.")
        else {
            thread(start = true) {
                washerStartsEvent.raiseEvent()
                if (!doWork((periodInMinutes * 60000).toLong(), washerFinishedEvent::raiseEvent)) return@thread
                deviceStateMachine.turnedOffState()
                washerTurnedOffEvent.raiseEvent()
            }
        } //todo what if electricity is turned off?
    }

    override fun generateReport(): IReport {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
