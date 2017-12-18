package onm.house.devices

import onm.configuration.DeviceType
import onm.configuration.json.DeviceConfig
import onm.events.DryerIsDoneEvent
import onm.events.IEventHandler
import onm.reports.IReport
import java.util.*

class Dryer(override val id: UUID,
            eventHandler: IEventHandler,
            deviceConfig: DeviceConfig,
            private val workingIntervalInMinutes: Double = 1.0) : AbstractDevice(DeviceType.FRIDGE, deviceConfig, eventHandler) {

    private val dryerIsDoneEvent = DryerIsDoneEvent(eventHandler, id)

    fun switchOn() {

        doWork((6000 * 1).toLong(), dryerIsDoneEvent::raiseEvent)
    }

    override fun generateReport(): IReport {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}