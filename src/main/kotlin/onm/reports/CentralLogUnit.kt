package onm.reports

open class CentralLogUnit protected constructor() : ICentralLogUnit {
    companion object {
        val instance by lazy { CentralLogUnit() }
    }

    private val allRecords = mutableListOf<IReport>()

    override fun filteredRecords(filter: (IReport) -> Boolean): Collection<IReport> {
        return allRecords.filter { x -> filter.invoke(x) }.toList()
    }

    override fun eventReports(filter: (EventReport) -> Boolean): Collection<EventReport> {
        return allRecords.filter { x -> x is EventReport && filter.invoke(x) }.map { x -> x as EventReport }.toList()
    }

    override fun deviceReports(filter: (DeviceReport) -> Boolean): Collection<DeviceReport> {
        return allRecords.filter { x -> x is DeviceReport && filter.invoke(x) }.map { x -> x as DeviceReport }.toList()
    }

    override fun humanReports(filter: (HumanReport) -> Boolean): Collection<HumanReport> {
        return allRecords.filter { x -> x is HumanReport && filter.invoke(x) }.map { x -> x as HumanReport }.toList()
    }

    override fun animalReports(filter: (AnimalReport) -> Boolean): Collection<AnimalReport> {
        return allRecords.filter { x -> x is AnimalReport && filter.invoke(x) }.map { x -> x as AnimalReport }.toList()
    }

    override fun roomReports(filter: (RoomReport) -> Boolean): Collection<RoomReport> {
        return allRecords.filter { x -> x is RoomReport && filter.invoke(x) }.map { x -> x as RoomReport }.toList()
    }

    override fun addReport(report: IReport) {
        allRecords.add(report)
    }
}