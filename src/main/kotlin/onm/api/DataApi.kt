package onm.api

import onm.house.devices.AbstractDevice

/**
 * Data api to every device monitoring consumption in all states
 */
class DataApi(private val device: AbstractDevice) {

    /**
     * @return Gets sum of consumption in all states
     */
    fun getTotalConsumption(): Double {
        return getIdleConsumption() + getTurnedOffConsumption() + getWorkingConsumption()
    }

    /**
     * @return Gets consumption in Idle state
     */
    fun getIdleConsumption(): Double {
        device.updateConsumption()
        return device.idleConsumption
    }

    /**
     * @return Gets consumption in working state
     */
    fun getWorkingConsumption(): Double {
        device.updateConsumption()
        return device.workingConsumption
    }

    /**
     * @return Gets consumption in turned off state
     */
    fun getTurnedOffConsumption(): Double {
        device.updateConsumption()
        return device.turnedOffConsumption
    }
}