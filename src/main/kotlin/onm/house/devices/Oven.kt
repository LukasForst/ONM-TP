package onm.house.devices

import onm.configuration.DeviceType
import onm.events.BakeFinishedEvent
import onm.events.IEventHandler
import onm.things.Food
import java.util.*

/**
 * Oven representation.
 * */
class Oven(override val id: UUID,
           eventHandler: IEventHandler) : AbstractDevice(DeviceType.OVEN) {

    private val ovenBakeFinishedEvent = BakeFinishedEvent(eventHandler)

    fun switchOn(food : Collection<Food>, minutes : Int){

        //TODO add execution in separate thread for $minutes minutes. Food size determines number of portions.

        ovenBakeFinishedEvent.raiseEvent()
    }

    override fun generateReport(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}