package onm.house.devices

import onm.configuration.DeviceType
import onm.events.IEventHandler
import onm.events.WasherDoneEvent
import java.util.*

/**
 * Wash machine representation
 * */
class Washer(override val id: UUID, eventHandler: IEventHandler) : AbstractDevice(DeviceType.WASHER) {

    private val event = WasherDoneEvent(eventHandler)

    /**
     * Starts washing clothes. This produces event which is raised after given time period. Note that new thread is created.
     * */
    fun startWashing(periodInMinutes: Double) {
        isBusy = true
        Thread(Runnable {
            Thread.sleep((periodInMinutes * 60000).toLong())
            isBusy = false
            event.raiseEvent()
        }).start()
    }

    override fun generateReport(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}