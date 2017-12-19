package onm.house.devices

import onm.api.DataApi
import onm.api.TelevisionControlApi
import onm.configuration.DeviceType
import onm.configuration.json.DeviceConfig
import onm.events.DeviceStartsEvent
import onm.events.DeviceTurnedOffEvent
import onm.events.IEventHandler
import onm.house.places.Room
import onm.human.HumanControlUnit
import onm.reports.IReport
import java.util.*

enum class TvStations {
    HBO, CNN, BBC, DISCOVERY_CHANNEL, BRAZZERS, MTV, CT1, CT24
}

class Television(override val id: UUID,
                 eventHandler: IEventHandler,
                 deviceConfig: DeviceConfig,
                 room: Room) : AbstractDevice(DeviceType.TELEVISION, deviceConfig, eventHandler, room) {

    init {
        HumanControlUnit.instance.registerDevice(this)
    }

    val televisionControlApi = TelevisionControlApi(this, id)
    override val dataApi = DataApi(this)

    private val random = Random()
    private lateinit var currentStation: TvStations

    private val televisionStartsEvent = DeviceStartsEvent(eventHandler, id,
            "Television named $deviceDescription is turned on.", this)
    private val televisionTurnedOffEvent = DeviceTurnedOffEvent(eventHandler, id,
            "Television named $deviceDescription is turned off", this)


    //TODO Check funcionality of this method
    fun getRandomChannel(): TvStations {
        return enumValues<TvStations>()[random.nextInt(TvStations.values().size)]
    }

    fun switchToChannel(channel: TvStations) {
        currentStation = channel
        log.info("Television $deviceDescription is switched to $currentStation channel")
    }

    fun turnOnTelevision() {
        if (deviceStateMachine.currentState.stateType != StateType.TURNED_OFF)
            log.error("Television named '$deviceDescription' is not turned off, therefore cannot be turned on.")
        else {
            deviceStateMachine.workingState()
            televisionStartsEvent.raiseEvent()
            switchToChannel(getRandomChannel())
        }
    }

    fun turnOffTelevision() {
        if (deviceStateMachine.currentState.stateType != StateType.WORKING)
            log.error("Television $deviceDescription is not turned on. Cannot turn it off.")
        else {
            deviceStateMachine.turnedOffState()
            televisionTurnedOffEvent.raiseEvent()
        }
    }

    override fun generateReport(): IReport {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

