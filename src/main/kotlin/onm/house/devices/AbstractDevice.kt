package onm.house.devices

import onm.configuration.DeviceType
import onm.configuration.json.PowerConsumption
import onm.house.places.Room
import onm.interfaces.StationaryEntity
import kotlin.concurrent.thread

/**
 * Abstract device class represents device in the house. All future devices should implements this class.
 * */
abstract class AbstractDevice(
        /**
         * Type of device.
         * */
        deviceType: DeviceType,

        /**
         * power consumption settings for this device
         * */
        powerConsumption: PowerConsumption) : StationaryEntity {


    /**
     * Time of device in idle state
     */
    var idleTime = 0.0;
    /**
     * Time of device in working state
     */
    var workingTime = 0.0;
    /**
     * Time of device in turnedOffTime
     */
    var turnedOffTime = 0.0;

    /**
     * Room reference. This should be set after adding device to the room.
     * */
    var room: Room? = null


    /**
     * Determines whether is device available to be used or not.
     * */
    val isDeviceAvailable: Boolean
        get() = deviceStateMachine.currentState.isDeviceAvailable

    /**
     * Returns current power consumption of the device. This is based on the state of the device.
     * */
    val currentPowerConsumption: Int
        get() = deviceStateMachine.currentState.currentPowerConsumption

    /**
     * State machine is used for manipulating with device power consumption.
     * */
    protected val deviceStateMachine = DeviceStateMachine(powerConsumption, deviceType, this)

    /**
     * Simulates work. After ending work it invokes callback.
     * */
    protected fun doWork(milliseconds: Long, callback: () -> Unit) {
        thread(start = true) {
            deviceStateMachine.workingState()
            Thread.sleep(milliseconds)
            deviceStateMachine.idleState()
            callback.invoke()
        }
    }
}