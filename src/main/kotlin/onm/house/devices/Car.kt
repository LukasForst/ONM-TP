package onm.house.devices

import onm.configuration.DeviceType
import onm.configuration.json.DeviceConfig
import onm.events.DeviceFinishedEvent
import onm.events.DeviceStartsEvent
import onm.events.IEventHandler
import onm.house.places.Room
import onm.human.Human
import onm.reports.IReport
import java.util.*

/**
 * Fucking awesome Tesla.
 * */
class Car(override val id: UUID, deviceConfig: DeviceConfig, eventHandler: IEventHandler, room: Room) : AbstractDevice(DeviceType.CAR, deviceConfig, eventHandler, room) {
    private val defaultAvgSpeed = 60

    override fun generateReport(): IReport {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun go(human: Human, distance: Int, avgSpeed: Int? = null) {
        val speed = avgSpeed ?: defaultAvgSpeed

        val carStartedEvent = DeviceStartsEvent(eventHandler, id, "Car started! Distance: $distance with avg speed $speed. With human ${human.name}")
        carStartedEvent.raiseEvent()

        val carStopedEvent = DeviceFinishedEvent(eventHandler, id, "Car stopped! Distance: $distance with avg speed $speed. With human ${human.name}")
        doWork(((distance / speed) * 60 * 60000).toLong(), carStopedEvent::raiseEvent)
    }
}