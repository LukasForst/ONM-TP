package onm.house.devices

import onm.configuration.DeviceType
import onm.configuration.json.PowerConsumption


/**
 * This data class represents current device state. In state, device has power consumption and available state
 * */
data class DeviceState(val currentPowerConsumption: Int, val isDeviceAvailable: Boolean)

/**
 * This class changes current state according called methods. Default is idleState
 * */
class DeviceStateMachine(
        private val powerConsumption: PowerConsumption,
        private val deviceType: DeviceType) {

    /**
     * Gets current state of the machine.
     * */
    var currentState = idleState()
        private set(value) {
            field = value
        }

    fun idleState(): DeviceState {
        currentState = DeviceState(powerConsumption.idleState ?: deviceType.idlePowerConsumption, true)
        return currentState
    }

    fun workingState(): DeviceState {
        currentState = DeviceState(powerConsumption.workingState ?: deviceType.workingPowerConsumption, false)
        return currentState
    }

    fun turnedOffState(): DeviceState {
        currentState = DeviceState(powerConsumption.turnedOffState ?: deviceType.turnedOffPowerConsumption, false)
        return currentState
    }
}