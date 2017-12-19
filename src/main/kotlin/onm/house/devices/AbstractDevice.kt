package onm.house.devices

import onm.configuration.DeviceType
import onm.configuration.json.DeviceConfig
import onm.events.DeviceBrokenEvent
import onm.events.IEvent
import onm.events.IEventHandler
import onm.events.RepairEvent
import onm.house.places.Room
import onm.human.HumanControlUnit
import onm.loggerFor
import java.util.*

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
        protected val eventHandler: IEventHandler,

        /**
         * Room reference. This should be set after adding device to the room.
         * */
        var room: Room) : IDevice {

    companion object {
        @JvmStatic
        protected val log = loggerFor(AbstractDevice::class.java)
    }

    init {
        HumanControlUnit.instance.registerDevice(this)
    }

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
     * Unique description of the device.
     * */
    override val deviceDescription: String
        get() = deviceConfig.name

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
     * Simulates work. After ending work it invokes callback.
     * @return true if everything goes well, false otherwise
     * */
    protected fun doWork(milliseconds: Long, callback: (() -> Unit)?): Boolean {
        val brokenEvent = verifyNotBroken(currentErrorProbability)
        return if (brokenEvent != null) {
            deviceStateMachine.brokenSate()
            brokenEvent.raiseEvent()
            false
        } else {
            currentErrorProbability += currentErrorProbability / 10
            deviceStateMachine.workingState()
            Thread.sleep(milliseconds) //TODO check if not broken after sleep whether animals and random events might destroy busy devices
            deviceStateMachine.idleState()
            callback?.invoke()
            true
        }
    }

    fun updateConsumption() {
        deviceStateMachine.addConsumption()
    }

    /**
     * Returns false if device is broken or working. True otherwise.
     */
    //TODO May be deleted in close future if not used
    protected fun isAvailable(): Boolean {
        val type = deviceStateMachine.currentState.stateType
        return !(type == StateType.BROKEN || type == StateType.WORKING)
    }

    fun repair(){
        deviceStateMachine.idleState()
        RepairEvent(eventHandler, this, id).raiseEvent()
    }

    protected var currentErrorProbability: Double = deviceConfig.breakageProbability ?: deviceType.breakageProbability

    protected fun verifyNotBroken(brokenChance: Double): IEvent? {
        val intervalNumber = (brokenChance * 10).toInt()
        val randomNumber = Random().nextInt(1000)

        return if (randomNumber >= intervalNumber) {
            null
        } else {
            DeviceBrokenEvent(eventHandler, this, id)
        }
    }
}