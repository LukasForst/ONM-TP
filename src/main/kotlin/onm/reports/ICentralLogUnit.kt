package onm.reports

import onm.events.IEvent

interface ICentralLogUnit {
    fun filteredRecords(filter: (IEvent) -> Boolean): Collection<IEvent>

    fun eventReports(filter: (EventReport) -> Boolean): Collection<EventReport>

    fun deviceReports(filter: (DeviceReport) -> Boolean): Collection<DeviceReport>

    fun humanReports(filter: (HumanReport) -> Boolean): Collection<HumanReport>

    fun animalReports(filter: (AnimalReport) -> Boolean): Collection<AnimalReport>

    fun roomReports(filter: (RoomReport) -> Boolean): Collection<RoomReport>

    fun addReport(report: IReport)
}