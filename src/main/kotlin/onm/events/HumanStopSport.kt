package onm.events

import onm.configuration.EventSeverity
import onm.human.Human
import java.util.*

class HumanStopSport(private val eventHandler: IEventHandler,
                     val human: Human, override val entityId: UUID) : IEvent { //todo probably will be better to use api

    override val message: String
        get() = "Human ${human.name} came home from doing sport"

    override val severity: EventSeverity
        get() = EventSeverity.INFO

    override fun raiseEvent() {
        eventHandler.handle(this)
    }
}