package onm.house.devices

import onm.api.BoilerControlApi
import onm.api.DataApi
import onm.configuration.DeviceType
import onm.configuration.json.DeviceConfig
import onm.events.IEventHandler
import onm.events.TemperatureEvent
import onm.house.ITemperatureHandler
import onm.house.Temperature
import onm.house.places.Room
import onm.reports.IReport
import java.util.*

class Boiler(override val id: UUID, deviceConfig: DeviceConfig, eventHandler: IEventHandler, room: Room, private val threshHold: Int) : AbstractDevice(DeviceType.BOILER, deviceConfig, eventHandler, room), ITemperatureHandler {
    override val dataApi = DataApi(this)

    val controlApi = BoilerControlApi(this)

    init {
        Temperature.instance.registerTemperatureHandler(this)
    }

    override fun generateReport(): IReport {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun temperatureChanged(old: Int, new: Int) {
        if (new <= threshHold) startHeating(threshHold + 4)
    }

    fun startHeating(finalTemp: Int) {
        val event = TemperatureEvent(eventHandler, id, "Boiler started and heated up.")
        doWork(((finalTemp - threshHold) * 30000).toLong(), {
            Temperature.instance.value = threshHold + 4
            event.raiseEvent()
        })
    }
}