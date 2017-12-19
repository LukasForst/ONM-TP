package onm.house.devices

import onm.api.DataApi
import onm.api.ToiletControlApi
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

class Toilet(override val id: UUID,
             eventHandler: IEventHandler,
             deviceConfig: DeviceConfig,
             room: Room) : AbstractDevice(DeviceType.TELEVISION, deviceConfig, eventHandler, room) {

    init {
        HumanControlUnit.instance.registerDevice(this)
    }

    override val dataApi = DataApi(this)
    val toiletControlApi = ToiletControlApi(this, id)

    private val flushingPeriodInSeconds = 7
    private val toiletFlushStartsEvent = DeviceStartsEvent(eventHandler, id, "Toilet $deviceDescription is being flushed.", this)
    private val toiletFlushFinishedEvent = DeviceFinishedEvent(eventHandler, id, "Toilet $deviceDescription flush is finished.", this)
    private val toiletEndsAll = DeviceTurnedOffEvent(eventHandler, id, "Toilet $deviceDescription is ready to use again.", this)

    fun flush() {
        if (deviceStateMachine.currentState.stateType == StateType.TURNED_OFF) {
            thread(start = true) {
                toiletFlushStartsEvent.raiseEvent()
                if (!doWork((flushingPeriodInSeconds * 1000).toLong(), toiletFlushFinishedEvent::raiseEvent)) return@thread

                Thread.sleep((flushingPeriodInSeconds * 1000).toLong()) //New water is being pulled.
                deviceStateMachine.turnedOffState()
                toiletEndsAll.raiseEvent()
            }
        } else {
            log.error("Toilet is in use or broken, cannot flush it. Oh Oh, embarrassing...")
        }
    }

    override fun generateReport(): IReport {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}