package onm.reports

import onm.configuration.DeviceType
import onm.configuration.EventSeverity
import onm.human.Human
import java.util.*

interface IReport {
    /**
     * Date for which is report valid.
     * */
    val date: Date

    /**
     * ID of the entity.
     * */
    val entityId: UUID

    /**
     * Report in which state is currently device
     * */
    val report: String
}

data class EventReport(
        override val date: Date,
        override val entityId: UUID,
        override val report: String,
        val eventSeverity: EventSeverity) : IReport

data class AnimalReport(
        override val date: Date,
        override val entityId: UUID,
        override val report: String,
        val animalType: DeviceType) : IReport


data class RoomReport(
        override val date: Date,
        override val entityId: UUID,
        override val report: String,
        val inRoomDevicesReports: Collection<DeviceReport>) : IReport

data class DeviceReport(
        override val date: Date,
        override val entityId: UUID,
        override val report: String,
        val deviceType: DeviceType) : IReport

data class HumanReport(
        override val date: Date,
        override val entityId: UUID,
        override val report: String,
        val Human: Human) : IReport //todo some description of human instead of whole instance

