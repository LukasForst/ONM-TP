package onm.api

import onm.house.devices.Car
import java.util.*

class CarControlApi(private val car: Car) : IControlApi {
    override val id: UUID
        get() = car.id

    /**
     * Lets drive!
     * */
    fun go(humanName: String, distance: Int, avgSpeed: Int? = null) {
        car.go(humanName, distance, avgSpeed)
    }
}