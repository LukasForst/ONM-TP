package onm.configuration

/**
 * DeviceType represents type of devices like fridge, oven etc. etc.
 * */
enum class DeviceType(
        /**
         * Power consumption in idle state.
         * */
        val idlePowerConsumption: Int,

        /**
         * Power consumption in running state.
         * */
        val workingPowerConsumption: Int,

        /**
         * Power consumption when device is turned off.
         * */
        val turnedOffPowerConsumption: Int) {

    WASHER(1, 2, 0),
    FRIDGE(1, 2, 0),
    OVEN(1, 2, 0);
    //todo add device type values and determine power consumption
}