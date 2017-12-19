package onm.house.devices

import onm.api.DataApi
import onm.api.WindowControlApi
import onm.configuration.DeviceType
import onm.configuration.json.DeviceConfig
import onm.events.IEventHandler
import onm.events.TemperatureEvent
import onm.house.ITemperatureHandler
import onm.house.Temperature
import onm.house.places.Room
import onm.reports.IReport
import java.lang.Math.abs
import java.util.*

class Window(override val id: UUID, deviceConfig: DeviceConfig, eventHandler: IEventHandler, room: Room, private val threshHold: Int) : AbstractDevice(DeviceType.BOILER, deviceConfig, eventHandler, room), ITemperatureHandler {
    override val dataApi = DataApi(this)

    val controlApi = WindowControlApi(this)

    init {
        Temperature.instance.registerTemperatureHandler(this)
    }

    override fun generateReport(): IReport {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun temperatureChanged(old: Int, new: Int) {
        if (new >= threshHold) openWindow(new - 1)
    }

    fun openWindow(currentTemp: Int) {
        val event = TemperatureEvent(eventHandler, id, "Window opened")
        doWork((abs(currentTemp - threshHold) * 30000).toLong(), {
            Temperature.instance.value = currentTemp
            event.raiseEvent()
        })
    }
}