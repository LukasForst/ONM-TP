package onm.house.devices

import onm.api.DataApi
import onm.configuration.DeviceType
import onm.configuration.json.DeviceConfig
import onm.events.DeviceBrokenEvent
import onm.events.IEvent
import onm.events.IEventHandler
import onm.events.RepairEvent
import onm.house.places.Room
import java.util.*
import kotlin.concurrent.thread

/**
 * Abstract device class represents device in the house. All future devices should implements this class.
 * */
abstract class AbstractDevice(
        /**
         * Type of device.
         * */
        val deviceType: DeviceType,

        /**
         * Device configuration
         * */
        private val deviceConfig: DeviceConfig,

        /**
         * Event handler used for handling raised events.
         * */
        protected val eventHandler: IEventHandler) : IDevice {

    /**
     * Time of device in idle state
     */
    var idleConsumption = 0.0
    /**
     * Time of device in working state
     */
    var workingConsumption = 0.0
    /**
     * Time of device in turnedOffTime
     */
    var turnedOffConsumption = 0.0

    /**
     * Room reference. This should be set after adding device to the room.
     * */
    var room: Room? = null

    /**
     * Unique description of the device.
     * */
    override val deviceDescription: String
        get() = deviceConfig.deviceDescription

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
    protected val deviceStateMachine = DeviceStateMachine(deviceConfig.powerConsumption, deviceType, this)

    /**
     * DataApi of this device to get consumption of energy
     */
    override val dataApi = DataApi(this)

    /**
     * Simulates work. After ending work it invokes callback.
     * */
    protected fun doWork(milliseconds: Long, callback: () -> Unit) {
        thread(start = true) {
            val brokenEvent = verifyNotBroken(currentErrorProbability)
            if (brokenEvent != null) {
                deviceStateMachine.brokenSate()
                brokenEvent.raiseEvent()
            } else {
                currentErrorProbability += currentErrorProbability / 10
                deviceStateMachine.workingState()
                Thread.sleep(milliseconds)
                deviceStateMachine.idleState()
                callback.invoke()
            }
        }
    }

    fun repair() {
        deviceStateMachine.idleState()
        RepairEvent(eventHandler, this, id).raiseEvent()
    }

    private var currentErrorProbability: Double = deviceConfig.breakageProbability ?: deviceType.breakageProbability

    private fun verifyNotBroken(brokenChance: Double): IEvent? {
        val intervalNumber = (brokenChance * 10).toInt()
        val randomNumber = Random().nextInt(1000)

        return if (randomNumber >= intervalNumber) {
            null
        } else {
            DeviceBrokenEvent(eventHandler, this, id)
        }
    }
}