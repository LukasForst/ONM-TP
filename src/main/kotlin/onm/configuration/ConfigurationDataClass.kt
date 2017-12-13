package onm.configuration

/**
 * Data class used as configuration part for out framework.
 * */
data class ConfigurationDataClass(
        /**
         * structure of this map is like that:
         *
         * ```RoomType: DeviceType - count```
         *
         * so example:
         *
         * ```Kitchen : Fridge : 1```
         * */
        val roomsAndDevices: Map<RoomConfig, Collection<DeviceConfig>> //todo add more config options
)

/**
 * Represents room config.
 * */
data class RoomConfig(
        val roomType: RoomType,
        val description: String,
        val floor: Int
)

/**
 * Represents device config.
 * */
data class DeviceConfig(
        val deviceType: DeviceType,
        val powerConsumption: PowerConsumption
)

/**
 * Data class which represents power consumptions in different devices states.
 * */
data class PowerConsumption(
        val idleState: Int? = null,
        val workingState: Int? = null,
        val turnedOffState: Int? = null
) {
    companion object {
        /**
         * Creates power consumption with default parameters
         * */
        fun defaultPowerConsumption(): PowerConsumption {
            return PowerConsumption()
        }
    }
}