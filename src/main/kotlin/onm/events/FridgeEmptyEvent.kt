package onm.events

import onm.configuration.EventSeverity
import onm.interfaces.EventHandler

/**
 * Event raised when fridge is empty.
 * */
class FridgeEmptyEvent(
        override val message: String,
        override val severity: EventSeverity,

        private val eventHandler: EventHandler) : Event {

    override fun raiseEvent() {
        eventHandler.handle(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FridgeEmptyEvent

        if (message != other.message) return false
        if (severity != other.severity) return false

        return true
    }

    override fun hashCode(): Int {
        var result = message.hashCode()
        result = 31 * result + severity.hashCode()
        return result
    }


}