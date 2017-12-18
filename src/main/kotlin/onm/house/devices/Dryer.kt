package onm.house.devices

import onm.api.DryerControlApi
import onm.configuration.DeviceType
import onm.configuration.json.DeviceConfig
import onm.events.IEventHandler
import onm.events.isFinishedEvent
import onm.house.places.Room
import onm.human.HumanControlUnit
import onm.reports.IReport
import java.util.*

class Dryer(override val id: UUID,
            eventHandler: IEventHandler,
            deviceConfig: DeviceConfig,
            room: Room) : AbstractDevice(DeviceType.FRIDGE, deviceConfig, eventHandler, room) {

    private val dryerIsDoneEvent = isFinishedEvent(eventHandler, id, "Drying clothes using $deviceDescription is done.")

    init {
        HumanControlUnit.instance.registerDevice(this)
    }

    val dryerControlApi = DryerControlApi(this, this.id)


    fun switchOn() {
        if (!isAvailable())
            log.error("Dryer named '$deviceDescription' is already working or broken, therefore cannot be switched on.")
        else {
            doWork((60000 * 10).toLong(), dryerIsDoneEvent::raiseEvent) //Ten minutes
            deviceStateMachine.turnedOffState()
        }
    }


    override fun generateReport(): IReport {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}