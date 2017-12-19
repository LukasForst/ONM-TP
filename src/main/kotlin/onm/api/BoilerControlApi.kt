package onm.api

import onm.house.devices.Boiler
import java.util.*

class BoilerControlApi(private val boiler: Boiler) : IControlApi {
    override val id: UUID
        get() = boiler.id

    /**
     * Temperature is changed to given value.
     * */
    fun changeTemperature(finalTemp: Int) {
        boiler.startHeating(finalTemp)
    }
}