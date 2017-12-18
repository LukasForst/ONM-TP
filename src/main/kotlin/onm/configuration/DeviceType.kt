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
        val turnedOffPowerConsumption: Int,

        /**
         * Double representing probability of breakage of the device in percentage.
         * */
        val breakageProbability: Double) {

    WASHER(1, 2, 0, 0.1),
    FRIDGE(1, 2, 0, 0.1),
    OVEN(1, 2, 0, 0.1),
    DRYER(1, 2, 0, 0.1);
    //todo add device type values and determine power consumption
}