package onm.events

import onm.animals.events.AnimalIsHungryEvent


/**
 * This class is used for handling events in whole app. It has protected constructor because of better testability.
 *
 * For unit testing please inherit this class. For getting its instance in production code use EventHandler.instance.
 * */
open class EventHandler protected constructor(): IEventHandler {

    companion object {
        /**
         * Gets instance as singleton.
         * */
        val instance by lazy { EventHandler() }
    }

    override fun handle(event: RepairEvent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handle(event: HumanDoSport) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handle(event: HumanStopSport) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handle(event: DryerIsDoneEvent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handle(event: AnimalIsHungryEvent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handle(event: DeviceBrokenEvent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handle(event: BakeFinishedEvent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handle(event: WasherDoneEvent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handle(event: FridgeEmptyEvent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}