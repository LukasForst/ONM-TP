package onm.house.devices

import onm.api.DryerControlApi
import onm.configuration.DeviceType
import onm.configuration.json.DeviceConfig
import onm.events.DryerIsDoneEvent
import onm.events.IEventHandler
import java.util.*

class Dryer(override val id: UUID,
            eventHandler: IEventHandler,
            deviceConfig: DeviceConfig,
            private val workingIntervalInMinutes: Double = 1.0) : AbstractDevice(DeviceType.FRIDGE, deviceConfig, eventHandler) {

    private val dryerIsDoneEvent = DryerIsDoneEvent(eventHandler)

    val dryerControlApi = DryerControlApi(this)

    fun switchOn() {

        doWork((6000 * 1).toLong(), dryerIsDoneEvent::raiseEvent)
    }

    override fun generateReport(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}