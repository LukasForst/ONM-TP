package onm.reports


interface ICentralLogUnit {
    fun filteredRecords(filter: (IReport) -> Boolean): Collection<IReport>

    fun eventReports(filter: (EventReport) -> Boolean): Collection<EventReport>

    fun deviceReports(filter: (DeviceReport) -> Boolean): Collection<DeviceReport>

    fun humanReports(filter: (HumanReport) -> Boolean): Collection<HumanReport>

    fun animalReports(filter: (AnimalReport) -> Boolean): Collection<AnimalReport>

    fun roomReports(filter: (RoomReport) -> Boolean): Collection<RoomReport>

    fun addReport(report: IReport)
}