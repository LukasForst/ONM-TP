package onm.events

import onm.animals.AnimalControlUnit
import onm.animals.events.AnimalIsHungryEvent
import onm.human.HumanControlUnit
import onm.human.HumanTask
import onm.human.TaskTypes
import onm.loggerFor
import onm.reports.*
import java.time.Instant
import kotlin.concurrent.thread


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
        val log = loggerFor(EventHandler::class.java)
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

    override fun handle(event: TemperatureEvent) {
        log.info(event.message)
        val device = humanControlUnit.availableThings.first { x -> x.id == event.entityId }
        logUnit.addReport(DeviceReport(Instant.now(), device.id, event.message, event.severity, device.deviceType, device.deviceDescription))
    }

    override fun handle(event: DeviceTurnedOffEvent) {
        TODO("not implemented")
    }

    override fun handle(event: DeviceStartsEvent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handle(event: DeviceFinishedEvent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handle(event: RepairEvent) {

        val device = event.device ?: humanControlUnit.availableThings.first()
        logUnit.addReport(DeviceReport(Instant.now(), event.entityId, event.message, event.severity, device.deviceType, device.deviceDescription))
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handle(event: HumanDoSport) {
        val human = event.human

        log.info((event.message))
        logUnit.addReport(HumanReport(Instant.now(), event.entityId, event.message, event.severity, human))
    }

    override fun handle(event: HumanStopSport) {
        val human = event.human

        log.info(event.message)
        logUnit.addReport(HumanReport(Instant.now(), event.entityId, event.message, event.severity, human))

    }


    override fun handle(event: AnimalIsHungryEvent) {
        log.info(event.message)
        logUnit.addReport(AnimalReport(Instant.now(), event.entityId, event.message, event.severity, event.animal.animalType))
        thread(start = true) {
            Thread.sleep(5000)
            animalControlUnit.feedAnimal(event.entityId)
        }
    }

    override fun handle(event: DeviceBrokenEvent) {
        val device = event.device ?: humanControlUnit.availableThings.first()
        val task = HumanTask(TaskTypes.REPAIR_DEVICE, device)
        humanControlUnit.queueTodo.add(task)

        log.info(event.message)


        val deviceReport = DeviceReport(Instant.now(), event.entityId, event.message, event.severity, device.deviceType, device.deviceDescription)
        logUnit.addReport(RoomReport(Instant.now(), device.room.id, deviceReport.toString(), event.severity, deviceReport))
        logUnit.addReport(deviceReport)
    }

    override fun handle(event: FridgeEmptyEvent) {
        val task = HumanTask(TaskTypes.SHOP, event.device)
        humanControlUnit.queueTodo.add(task)

        log.info(event.message)

        val device = event.device

        val deviceReport = DeviceReport(Instant.now(), event.entityId, event.message, event.severity,
                device.deviceType, device.deviceDescription)
        val room = device.room

        logUnit.addReport(RoomReport(Instant.now(), room.id, deviceReport.toString(), event.severity, deviceReport))
        logUnit.addReport(deviceReport)
    }

    private fun handleDeviceDoneEvent(event: IEvent) {
        val device = humanControlUnit.availableThings.first { x -> x.id == event.entityId }
        val deviceReport = DeviceReport(Instant.now(), event.entityId, event.message, event.severity, device.deviceType, device.deviceDescription)
        val room = device.room

        logUnit.addReport(RoomReport(Instant.now(), room.id, deviceReport.toString(), event.severity, deviceReport))
        logUnit.addReport(deviceReport)
    }
}