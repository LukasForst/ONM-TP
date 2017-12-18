package onm.events

import onm.animals.events.AnimalIsHungryEvent


/**
 * Handler which will react on all types of events. Works on dispatch pattern.
 * */
interface IEventHandler {
    //todo implement global handler for all events
    fun handle(event: isFinishedEvent)

    fun handle(event: FridgeEmptyEvent)

    fun handle(event: DeviceBrokenEvent)

    fun handle(event: AnimalIsHungryEvent)

    fun handle(event: RepairEvent)

    fun handle(event: HumanDoSport)

    fun handle(event: HumanStopSport)
}