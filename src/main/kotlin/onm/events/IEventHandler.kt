package onm.events

/**
 * Handler which will react on all types of events. Works on dispatch pattern.
 * */
interface IEventHandler {
    //todo implement global handler for all events
    fun handle(event: FridgeEmptyIEvent)

    fun handle(event: WasherDoneIEvent)
}