package onm.api

import onm.house.devices.Television
import onm.house.devices.TvStations
import java.util.*

/**
 * Class reperesents api of a television. Television may be turned on, turned off, user may switch channels.
 */
class TelevisionControlApi(
        private val television: Television,
        override val id: UUID) : IControlApi {

    /**
     * If television is in TurnedOffState, it will be turned on. Otherwise error is logged.
     */
    fun turnOn() {
        television.turnOnTelevision()
    }

    /**
     * If television is in WorkingState, it will be turned off. Otherwise error is logged.
     */
    fun turnOff() {
        television.turnOffTelevision()
    }

    /**
     * @param desiredChannel TvStation enum to be set on tv.
     */
    fun switchChannel(desiredChannel: TvStations) {
        television.switchToChannel(desiredChannel)
    }

    /**
     * Switch television to random channel in enum TvStations
     */
    fun switchToRandomChannel() {
        television.switchToChannel(television.getRandomChannel())
    }

    /**
     * No need to comment...
     */
    fun prepareToFap() {
        television.switchToChannel(TvStations.BRAZZERS)
    }

}