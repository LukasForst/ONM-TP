package onm.house.devices

import onm.api.DryerControlApi
import onm.configuration.DeviceType
import onm.configuration.json.DeviceConfig
import onm.events.DryerIsDoneEvent
import onm.events.IEventHandler
import onm.reports.IReport
import java.util.*

class Dryer(override val id: UUID,
            eventHandler: IEventHandler,
            deviceConfig: DeviceConfig) : AbstractDevice(DeviceType.FRIDGE, deviceConfig, eventHandler) {

    private val dryerIsDoneEvent = DryerIsDoneEvent(eventHandler, id)

    val dryerControlApi = DryerControlApi(this, this.id)


    fun switchOn() {
        if (!isAvailable())
            log.error("Dryer named '${deviceDescription}' is already working or broken, therefore cannot be switched on.")
        else
            doWork((60000 * 10).toLong(), dryerIsDoneEvent::raiseEvent) //Ten minutes
        deviceStateMachine.turnedOffState()
    }


    override fun generateReport(): IReport {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}