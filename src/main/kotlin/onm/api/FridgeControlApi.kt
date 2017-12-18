package onm.api

import onm.house.devices.Fridge
import onm.things.Food
import java.util.*

/**
 * Class representing api of fridge. Fridge stores food.
 */
class FridgeControlApi(
        private val fridge: Fridge, override val id: UUID) : IControlApi {


    /**
     * Get collection representing food in the fridge. When collection is empty FridgeEmptyEvent is raised.
     * @return content of fridge in linkedList<Food>
     */
    fun getFood() : LinkedList<Food> {
        return fridge.food
    }

    /**
     * Adds food in the fridge.
     * @param food food collection to be added to fridge
     * */
    fun addFood(food: Collection<Food>) {
        fridge.getListOfFood().addAll(food)
    }

    /**
     * Adds food in the fridge.
     * @param food food to be added to fridge
     * */
    fun addFood(food: Food) {
        fridge.getListOfFood().add(food)
    }


}