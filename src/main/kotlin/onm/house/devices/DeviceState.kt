package onm.house.devices

import onm.configuration.DeviceType
import onm.configuration.json.PowerConsumption


/**
 * This data class represents current device state. In state, device has power consumption and available state
 * */
data class DeviceState(val currentPowerConsumption: Int, val isDeviceAvailable: Boolean, var stateType : StateType)

enum class StateType(){
    IDLE, WORKING, TURNED_OFF
}


/**
 * This class changes current state according called methods. Default is idleState
 * */
class DeviceStateMachine(
        private val powerConsumption: PowerConsumption,
        private val deviceType: DeviceType,
        private val device: AbstractDevice,
        private var timeOfLastChange: Long = System.currentTimeMillis()) {



    private fun getElapsedMinutes() : Float {
        val elapsedTimeMilis = System.currentTimeMillis() - timeOfLastChange
        return elapsedTimeMilis/(60*1000F)
    }

    private fun addElapsedTime(){
        when (currentState.stateType){
            StateType.TURNED_OFF -> device.turnedOffTime += getElapsedMinutes()
            StateType.WORKING -> device.workingTime += getElapsedMinutes()
            StateType.IDLE -> device.idleTime += getElapsedMinutes()
        }
        timeOfLastChange = System.currentTimeMillis()
    }

    /**
     * Gets current state of the machine.
     * */
    var currentState = idleState()
        private set(value) {
            field = value
        }

    fun idleState(): DeviceState {
        addElapsedTime()
        currentState = DeviceState(powerConsumption.idleState ?: deviceType.idlePowerConsumption, true, StateType.IDLE)
        return currentState
    }

    fun workingState(): DeviceState {
        addElapsedTime()
        currentState = DeviceState(powerConsumption.workingState ?: deviceType.workingPowerConsumption, false, StateType.WORKING)
        return currentState
    }

    fun turnedOffState(): DeviceState {
        addElapsedTime()
        currentState = DeviceState(powerConsumption.turnedOffState ?: deviceType.turnedOffPowerConsumption, false, StateType.TURNED_OFF)
        return currentState
    }
}