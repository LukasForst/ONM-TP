package onm.events

import onm.animals.AnimalControlUnit
import onm.animals.events.AnimalIsHungryEvent
import onm.human.HumanControlUnit
import onm.reports.*
import java.time.Instant


/**
 * This class is used for handling events in whole app. It has protected constructor because of better testability.
 *
 * For unit testing please inherit this class. For getting its instance in production code use EventHandler.instance.
 * */
open class EventHandler protected constructor() : IEventHandler {
    companion object {
        /**
         * Gets instance as singleton.
         * */
        val instance by lazy { EventHandler() }
    }

    private lateinit var logUnit: ICentralLogUnit
    private lateinit var humanControlUnit: HumanControlUnit
    private lateinit var animalControlUnit: AnimalControlUnit

    override fun register(humanControlUnit: HumanControlUnit) {
        this.humanControlUnit = humanControlUnit
    }

    override fun register(logUnit: CentralLogUnit) {
        this.logUnit = logUnit
    }

    override fun register(animalControlUnit: AnimalControlUnit) {
        this.animalControlUnit = animalControlUnit
    }

    override fun handle(event: isFinishedEvent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handle(event: RepairEvent) {

        val device = event.device ?: humanControlUnit.availableThings.first()
        logUnit.addReport(DeviceReport(Instant.now(), event.entityId, event.message, event.severity, device.deviceType, device.deviceDescription))
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handle(event: HumanDoSport) {

        val human = event.human ?: humanControlUnit.humans.first()
        logUnit.addReport(HumanReport(Instant.now(), event.entityId, event.message, event.severity, human))
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handle(event: HumanStopSport) {

        val human = event.human ?: humanControlUnit.humans.first()
        logUnit.addReport(HumanReport(Instant.now(), event.entityId, event.message, event.severity, human))
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun handle(event: AnimalIsHungryEvent) {

        logUnit.addReport(AnimalReport(Instant.now(), event.entityId, event.message, event.severity, event.animal.animalType))
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handle(event: DeviceBrokenEvent) {

        val device = event.device ?: humanControlUnit.availableThings.first()
        logUnit.addReport(DeviceReport(Instant.now(), event.entityId, event.message, event.severity, device.deviceType, device.deviceDescription))
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handle(event: FridgeEmptyEvent) {
        handleDeviceDoneEvent(event)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun handleDeviceDoneEvent(event: IEvent) {
        val device = humanControlUnit.availableThings.first { x -> x.id == event.entityId }
        val deviceReport = DeviceReport(Instant.now(), event.entityId, event.message, event.severity, device.deviceType, device.deviceDescription)
        val room = device.room

        logUnit.addReport(RoomReport(Instant.now(), room.id, deviceReport.toString(), event.severity, deviceReport))
        logUnit.addReport(deviceReport)
    }
}