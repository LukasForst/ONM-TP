package onm.house.devices

import onm.api.DryerControlApi
import onm.configuration.DeviceType
import onm.configuration.EventSeverity
import onm.configuration.json.DeviceConfig
import onm.events.DeviceFinishedEvent
import onm.events.DeviceStartsEvent
import onm.events.DeviceTurnedOffEvent
import onm.events.IEventHandler
import onm.house.places.Room
import onm.human.HumanControlUnit
import onm.reports.DeviceReport
import onm.reports.IReport
import java.time.Instant
import java.util.*
import kotlin.concurrent.thread

class Dryer(override val id: UUID,
            eventHandler: IEventHandler,
            deviceConfig: DeviceConfig,
            room: Room) : AbstractDevice(DeviceType.FRIDGE, deviceConfig, eventHandler, room) {

    private val dryerIsDoneEvent = DeviceFinishedEvent(eventHandler, id, "Drying clothes using $deviceDescription is done.")
    private val dryerStartsEvent = DeviceStartsEvent(eventHandler, id, "Dryer named $deviceDescription is turned on.")
    private val dryerTurnedOffEvent = DeviceTurnedOffEvent(eventHandler, id, "Dryer $deviceDescription is turned off.")
    private val dryingTime = (60000 * 10).toLong() //Ten minutes

    init {
        HumanControlUnit.instance.registerDevice(this)
    }

    val dryerControlApi = DryerControlApi(this, id)


    fun switchOn() {
        if (deviceStateMachine.currentState.stateType != StateType.TURNED_OFF)
            log.error("Dryer named '$deviceDescription' is not turned off, therefore cannot be switched on.")
        else {
            thread(start = true) {
                dryerStartsEvent.raiseEvent()
                if (!doWork(dryingTime, dryerIsDoneEvent::raiseEvent)) return@thread
                deviceStateMachine.turnedOffState()
                dryerTurnedOffEvent.raiseEvent()
            }
        }
    }


    override fun generateReport(): IReport {
        return DeviceReport(Instant.now(), id, "Dryer is ${deviceStateMachine.currentState.stateType}", EventSeverity.INFO, DeviceType.DRYER, deviceDescription)
    }
}