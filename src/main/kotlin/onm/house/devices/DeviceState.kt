package onm.house.devices

import onm.configuration.DeviceType
import onm.configuration.json.PowerConsumption


/**
 * This data class represents current device state. In state, device has power consumption and available state
 * */
data class DeviceState(val currentPowerConsumption: Int, val isDeviceAvailable: Boolean, var stateType : StateType, val isBroken: Boolean = false)


//TODO this data clas and enum can be merged to just enum (if isDeviceAvailable is still unused)
enum class StateType {
    IDLE, WORKING, TURNED_OFF, BROKEN
}

/**
 * This class changes current state according called methods. Default is idleState
 * */
class DeviceStateMachine(
        private val powerConsumption: PowerConsumption,
        private val deviceType: DeviceType,
        private val device: AbstractDevice,
        private var timeOfLastChange: Long = System.currentTimeMillis()) {

    /**
     * Consumed energy is calculated this way: _elapsedMinutes_ * cur_power_consumption
     */
    private fun getConsumedEnergy(): Float {
        val elapsedTimeMilis = System.currentTimeMillis() - timeOfLastChange
        return elapsedTimeMilis / (60 * 1000F) * currentState.currentPowerConsumption
    }

    /**
     * Add consumption to device according to current state
     */
    private fun addConsumption() {
        when (currentState.stateType){
            StateType.TURNED_OFF -> device.turnedOffConsumption += getConsumedEnergy()
            StateType.WORKING -> device.workingConsumption += getConsumedEnergy()
            StateType.IDLE -> device.idleConsumption += getConsumedEnergy()
            //TODO
        }
        timeOfLastChange = System.currentTimeMillis()
    }

    /**
     * Gets current state of the machine.
     * */
    var currentState = idleState()
        private set(value) {
            addConsumption()
            field = value
        }

    fun idleState(): DeviceState {
        addConsumption()
        currentState = DeviceState(powerConsumption.idleState ?: deviceType.idlePowerConsumption, true, StateType.IDLE)
        return currentState
    }

    fun workingState(): DeviceState {
        addConsumption()
        currentState = DeviceState(powerConsumption.workingState ?: deviceType.workingPowerConsumption, false, StateType.WORKING)
        return currentState
    }

    fun turnedOffState(): DeviceState {
        addConsumption()
        currentState = DeviceState(powerConsumption.turnedOffState ?: deviceType.turnedOffPowerConsumption, false, StateType.TURNED_OFF)
        return currentState
    }

    fun brokenSate(): DeviceState {
        addConsumption()
        currentState = DeviceState(powerConsumption.turnedOffState ?: deviceType.turnedOffPowerConsumption, false, StateType.BROKEN, true)
        return currentState
    }
}