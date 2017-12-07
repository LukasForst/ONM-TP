package onm.house.devices

import onm.configuration.DeviceType
import onm.events.FridgeEmptyEvent
import onm.events.IEventHandler
import onm.things.Food
import java.util.*

/**
 * Fridge representation.
 * */
class Fridge(override val id: UUID,
             eventHandler: IEventHandler) : AbstractDevice(DeviceType.FRIDGE) {

    private val fridgeEmptyEvent = FridgeEmptyEvent(eventHandler)
    private val _food = LinkedList<Food>()

    /**
     * Get collection representing food in the fridge. When collection is empty FridgeEmptyEvent is raised.
     * */
    val food: Collection<Food>
        get() {
            if (_food.isEmpty()) {
                fridgeEmptyEvent.raiseEvent()
                //todo This event is executed in main thread, execute it in the separate thread for better performance
            }

            return _food
        }

    /**
     * Adds food in the fridge.
     * */
    fun addFood(food: Collection<Food>) {
        _food.addAll(food)
    }

    /**
     * Adds food in the fridge.
     * */
    fun addFood(food: Food) {
        _food.add(food)
    }


    override fun generateReport(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}