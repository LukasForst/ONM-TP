package onm.api

import onm.house.Temperature
import onm.house.devices.Window
import java.util.*


class WindowControlApi(private val window: Window) : IControlApi {
    override val id: UUID
        get() = window.id

    /**
     * Opens window, temperature is changed.
     * */
    fun changeTemperature() {
        window.openWindow(Temperature.instance.value)
    }
}