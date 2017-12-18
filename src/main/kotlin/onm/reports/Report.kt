package onm.reports

import onm.animals.AnimalType
import onm.configuration.DeviceType
import onm.configuration.EventSeverity
import onm.human.Human
import java.time.Instant
import java.util.*

interface IReport {
    /**
     * Date for which is report valid.
     * */
    val date: Instant

    /**
     * ID of the entity.
     * */
    val entityId: UUID

    /**
     * Event severity
     * */
    val severity: EventSeverity

    /**
     * Report in which state is currently device
     * */
    val report: String
}

data class EventReport(
        override val date: Instant,
        override val entityId: UUID,
        override val report: String,
        override val severity: EventSeverity,
        val eventSeverity: EventSeverity) : IReport

data class AnimalReport(
        override val date: Instant,
        override val entityId: UUID,
        override val report: String,
        override val severity: EventSeverity,
        val animalType: AnimalType) : IReport


data class RoomReport(
        override val date: Instant,
        override val entityId: UUID,
        override val report: String,
        override val severity: EventSeverity,
        val deviceReport: DeviceReport) : IReport

data class DeviceReport(
        override val date: Instant,
        override val entityId: UUID,
        override val report: String,
        override val severity: EventSeverity,
        val deviceType: DeviceType,
        val deviceDescription: String) : IReport

data class HumanReport(
        override val date: Instant,
        override val entityId: UUID,
        override val report: String,
        override val severity: EventSeverity,
        val Human: Human) : IReport //todo some description of human instead of whole instance

