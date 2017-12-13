package onm.house.devices

import onm.configuration.DeviceType
import onm.configuration.PowerConsumption
import onm.events.BakeFinishedEvent
import onm.events.IEventHandler
import onm.things.Food
import java.util.*

/**
 * Oven representation.
 * */
class Oven(override val id: UUID,
           eventHandler: IEventHandler,
           powerConsumption: PowerConsumption = PowerConsumption.defaultPowerConsumption())
    : AbstractDevice(DeviceType.OVEN, powerConsumption) {

    private val ovenBakeFinishedEvent = BakeFinishedEvent(eventHandler)

    fun switchOn(food: Collection<Food>, minutes: Double) {
        doWork((minutes * 60000).toLong(), ovenBakeFinishedEvent::raiseEvent)
        //TODO Food size determines number of portions.
    }

    override fun generateReport(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}