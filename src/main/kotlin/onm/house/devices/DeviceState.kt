package onm.house.devices

import onm.configuration.DeviceType
import onm.configuration.PowerConsumption


data class DeviceState(val currentPowerConsumption: Int, val isDeviceAvailable: Boolean)

class DeviceStateMachine(
        private val powerConsumption: PowerConsumption,
        private val deviceType: DeviceType) {

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