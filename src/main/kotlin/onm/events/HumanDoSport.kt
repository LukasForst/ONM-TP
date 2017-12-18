package onm.events

import onm.configuration.EventSeverity
import onm.human.Human
import java.util.*

class HumanDoSport(private val eventHandler: IEventHandler,
                   /**
                    * Reference to human which go sport.
                    * */
                   val human: Human?, override val entityId: UUID) : IEvent { //todo probably will be better to use api

    override val message: String
        get() {
            return if (human != null) {
                "Human ${human.name} do sport"
            } else {
                "Random human do sport!"
            }
        }
    override val severity: EventSeverity
        get() = EventSeverity.INFO

    override fun raiseEvent() {
        eventHandler.handle(this)
    }
}