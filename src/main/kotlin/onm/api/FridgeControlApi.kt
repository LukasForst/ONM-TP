package onm.api

import onm.house.devices.Fridge
import onm.things.Food
import java.util.*

//TODO add javadoc beacuse this is API
class FridgeControlApi(
        private val fridge : Fridge){


    /**
     * Get collection representing food in the fridge. When collection is empty FridgeEmptyEvent is raised.
     */
    fun getFood() : LinkedList<Food> {
        return fridge.food
    }

    /**
     * Adds food in the fridge.
     * */
    fun addFood(food: Collection<Food>) {
        fridge.food.addAll(food)
    }

    /**
     * Adds food in the fridge.
     * */
    fun addFood(food: Food) {
        fridge.food.add(food)
    }


}