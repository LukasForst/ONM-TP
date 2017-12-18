package onm.configuration.json

import onm.animals.AnimalType
import onm.configuration.DeviceType
import onm.configuration.FurnitureType
import onm.configuration.RoomType
import onm.things.Equipment

/**
 * Classes in this file will be serialized to the JSON.
 * */

/**
 * Data class used as configuration part for out framework.
 * */
data class ConfigurationDataClass(
        val rooms: Collection<RoomConfig>,
        val vehicles: Collection<VehicleConfig>,
        val animals: Collection<AnimalConfig>,
        val equipments: Collection<EquipmentConfig>
)

/**
 * Represents room config.
 * */
data class RoomConfig(
        val type: RoomType,
        val name: String,
        val floor: Int = 0,
        val devices: Collection<DeviceConfig>,
        val furniture: Collection<FurnitureConfig>
)

/**
 * Represents device config.
 * */
data class DeviceConfig(
        val type: DeviceType,
        val name: String, // TODO deviceDescription must be unique, therefore it has to be checked while parsing json
        val powerConsumption: PowerConsumption = PowerConsumption(),
        val breakageProbability: Double? = null
)

/**
 * Represent furniture config
 */
data class FurnitureConfig(
        val type: FurnitureType,
        val name: String
)

/**
 * Data class which represents power consumptions in different devices states.
 * */
data class PowerConsumption(
        val idleState: Int? = null,
        val workingState: Int? = null,
        val turnedOffState: Int? = null
)

/**
 * Represent vehicle config
 */
data class VehicleConfig(
        val name: String,
        val powerConsumption: PowerConsumption,
        val breakageProbability: Double? = null
)

/**
 * Represent animal config
 */
data class AnimalConfig(
        val name: String,
        val type: AnimalType
)

/**
 * Represent equipment config
 */
data class EquipmentConfig(
        val name: String,
        val type: Equipment
)