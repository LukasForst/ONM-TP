package onm.configuration.json

import onm.configuration.DeviceType
import onm.configuration.RoomType

/**
 * Classes in this file will be serialized to the JSON.
 * */

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
        val roomsAndDevices: Map<RoomConfig, Collection<DeviceConfig>> //todo add more config options + how to save generic collection to the json
)

/**
 * Represents room config.
 * */
data class RoomConfig(
        val roomType: RoomType,
        val description: String?,
        val floor: Int
)

/**
 * Represents device config.
 * */
data class DeviceConfig(
        val deviceType: DeviceType,
        val powerConsumption: PowerConsumption = PowerConsumption(),
        val deviceDescription: String, // TODO deviceDescription must be unique, therefore it has to be checked while parsing json
        val breakageProbability: Double? = null
)

/**
 * Data class which represents power consumptions in different devices states.
 * */
data class PowerConsumption(
        val idleState: Int? = null,
        val workingState: Int? = null,
        val turnedOffState: Int? = null
)