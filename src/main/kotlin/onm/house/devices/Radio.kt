package onm.house.devices

import onm.api.DataApi
import onm.api.RadioControlApi
import onm.configuration.DeviceType
import onm.configuration.json.DeviceConfig
import onm.events.DeviceStartsEvent
import onm.events.DeviceTurnedOffEvent
import onm.events.IEventHandler
import onm.house.places.Room
import onm.human.HumanControlUnit
import onm.reports.IReport
import java.util.*

class Radio(override val id: UUID,
            eventHandler: IEventHandler,
            deviceConfig: DeviceConfig,
            room: Room) : AbstractDevice(DeviceType.RADIO, deviceConfig, eventHandler, room) {

    init {
        HumanControlUnit.instance.registerDevice(this)
    }

    private val radioStartsEvent = DeviceStartsEvent(eventHandler, id, "Radio $deviceDescription is turned on.")
    private val radioTurnedOffEvent = DeviceTurnedOffEvent(eventHandler, id, "Radio $deviceDescription is turned off.")

    override val dataApi = DataApi(this)
    val radioControlApi = RadioControlApi(this, id)

    private fun didItBreak(): Boolean {
        val brokenEvent = verifyNotBroken(currentErrorProbability)
        return if (brokenEvent != null) {
            deviceStateMachine.brokenSate()
            brokenEvent.raiseEvent()
            true
        } else
            false
    }

    fun turnOn() {
        if (deviceStateMachine.currentState.stateType != StateType.TURNED_OFF) {
            log.error("Radio $deviceDescription is not turned off. Cannot turn it on.")
        } else {
            radioStartsEvent.raiseEvent()

            if (didItBreak()) return

            deviceStateMachine.workingState()
        }
    }

    fun turnOff() {
        if (deviceStateMachine.currentState.stateType != StateType.WORKING) {
            log.error("Radio $deviceDescription is not turned on. Cannot turn it on.")
        } else {
            if (didItBreak()) return

            deviceStateMachine.turnedOffState()
            radioTurnedOffEvent.raiseEvent()
        }


    }

    override fun generateReport(): IReport {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}