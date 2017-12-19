package onm.api

import onm.configuration.json.DocumentationLoader
import onm.house.devices.AbstractDevice

/**
 * Data api to every device monitoring consumption in all states
 */
class DataApi(private val device: AbstractDevice) {

    /**
     * Documentation for this particular device. It is lazy loaded from json config file.
     * */
    val documentation: String? by lazy { DocumentationLoader.loadConfigFor(device.deviceType) }


    /**
     * Gets sum of consumption in all states
     */
    val totalConsumption: Double
        get() {
            return idleConsumption + turnedOffConsumption + workingConsumption
        }


    /**
     *  Gets consumption in Idle state
     */
    val idleConsumption: Double
        get() {
            device.updateConsumption()
            return device.idleConsumption
        }


    /**
     * Gets consumption in working state
     */
    val workingConsumption: Double
        get() {
            device.updateConsumption()
            return device.workingConsumption
        }

    /**
     * Gets consumption in turned off state
     */
    val turnedOffConsumption: Double
        get() {
            device.updateConsumption()
            return device.turnedOffConsumption
        }
}