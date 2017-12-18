package onm.events

import onm.configuration.EventSeverity
import onm.house.devices.AbstractDevice
import onm.human.Human
import java.util.*

class HumanStopSport(private val eventHandler: IEventHandler,
                     val human: Human?, override val entityId: UUID) : IEvent { //todo probably will be better to use api

    override val message: String
        get() {
            return if (human != null) {
                "Human ${human.name} is come home"
            } else {
                "Random human come home"
            }
        }
    override val severity: EventSeverity
        get() = EventSeverity.INFO

    override fun raiseEvent() {
        eventHandler.handle(this)
    }
}