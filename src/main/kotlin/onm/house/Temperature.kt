package onm.house

open class Temperature protected constructor(private var _value: Int) {
    companion object {
        val instance by lazy { Temperature(23) }
    }

    private val handlers = mutableListOf<ITemperatureHandler>()

    var value: Int
        get() = _value
        set(value) {
            val oldOne = _value
            _value = value
            handlers.forEach { x -> x.temperatureChanged(oldOne, value) }
        }

    fun registerTemperatureHandler(handler: ITemperatureHandler) {
        handlers.add(handler)
    }
}

interface ITemperatureHandler {
    fun temperatureChanged(old: Int, new: Int)
}