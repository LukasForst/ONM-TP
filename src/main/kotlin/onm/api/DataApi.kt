package onm.api

import onm.house.devices.AbstractDevice

//TODO add javadoc beacuse this is API
class DataApi(private val device: AbstractDevice) {

    /**
     * Gets sum of consumption in all states
     */
    fun getTotalConsumption(): Double {
        return getIdleConsumption() + getTurnedOffConsumption() + getWorkingConsumption()
    }

    /**
     * Gets consumption in Idle state
     */
    fun getIdleConsumption(): Double {
        return device.idleConsumption
    }

    /**
     * Gets consumption in working state
     */
    fun getWorkingConsumption(): Double {
        return device.workingConsumption
    }

    /**
     * Gets consumption in turned off state
     */
    fun getTurnedOffConsumption(): Double {
        return device.turnedOffConsumption
    }
}