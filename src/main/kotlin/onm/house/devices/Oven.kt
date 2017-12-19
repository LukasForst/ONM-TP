package onm.house.devices

import onm.api.OvenControlApi
import onm.configuration.DeviceType
import onm.configuration.json.DeviceConfig
import onm.events.DeviceFinishedEvent
import onm.events.DeviceStartsEvent
import onm.events.DeviceTurnedOffEvent
import onm.events.IEventHandler
import onm.house.places.Room
import onm.human.HumanControlUnit
import onm.reports.IReport
import onm.things.Food
import java.util.*
import kotlin.concurrent.thread

/**
 * Oven representation.
 * */
class Oven(override val id: UUID,
           eventHandler: IEventHandler,
           deviceConfig: DeviceConfig,
           room: Room)
    : AbstractDevice(DeviceType.OVEN, deviceConfig, eventHandler, room) {

    init {
        HumanControlUnit.instance.registerDevice(this)
    }

    private val ovenBakeFinishedEvent = DeviceFinishedEvent(eventHandler, id, "Baking using $deviceDescription is done.")
    private val ovenStartsEvent = DeviceStartsEvent(eventHandler, id, "Oven named $deviceDescription is turned on.")
    private val ovenTurnedOffEvent = DeviceTurnedOffEvent(eventHandler, id, "Oven $deviceDescription is turned off.")

    val ovenControlApi = OvenControlApi(this, id)

    fun switchOn(food: Collection<Food>, minutes: Double) {
        if (deviceStateMachine.currentState.stateType != StateType.TURNED_OFF)
            log.error("Oven named '$deviceDescription' is not turned off, therefore cannot be switched on.")
        else {
            thread(start = true) {
                ovenStartsEvent.raiseEvent()
                if (!doWork((minutes * 60000).toLong(), ovenBakeFinishedEvent::raiseEvent)) return@thread
                deviceStateMachine.turnedOffState()
                ovenTurnedOffEvent.raiseEvent()
            }
        }
        //TODO Food size determines number of portions.
    }

    override fun generateReport(): IReport {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}