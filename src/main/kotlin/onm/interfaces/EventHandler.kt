package onm.interfaces

import onm.events.FridgeEmptyEvent
import onm.events.WasherDoneEvent

/**
 * Handler which will react on all types of events. Works on dispatch pattern.
 * */
interface EventHandler {
    //todo implement global handler for all events
    fun handle(event: FridgeEmptyEvent)

    fun handle(event: WasherDoneEvent)
}