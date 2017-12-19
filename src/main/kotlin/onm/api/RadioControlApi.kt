package onm.api

import onm.house.devices.Radio
import java.util.*

/**
 * Class reperesents api of a radio. Radio may be turned on and turned off.
 */
class RadioControlApi(
        private val radio: Radio,
        override val id: UUID) : IControlApi {

    /**
     * Turns the radio on unless it is not turned off. If it isn't turned off, log error is made.
     */
    fun turnOn() {
        radio.turnOn()
    }

    /**
     * Turns the radio off unless it is not turned on. If it isn't turned on, log error is made.
     */
    fun turnOff() {
        radio.turnOff()
    }

}