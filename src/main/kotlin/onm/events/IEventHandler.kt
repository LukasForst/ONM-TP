package onm.events


/**
 * Handler which will react on all types of events. Works on dispatch pattern.
 * */
interface IEventHandler {
    //todo implement global handler for all events
    fun handle(event: FridgeEmptyEvent)

    fun handle(event: WasherDoneEvent)

    fun handle(event: BakeFinishedEvent)

    fun handle(event: KettleDoneEvent)
}