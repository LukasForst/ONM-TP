package onm.things

/**
 * Food data class.
 * */
data class Food(val type: FoodType) //todo add all necessary fields for food representation

enum class FoodType {
    APPLE, CUCUMBER, BREAD, CHOCOLATE, EGG //todo add more food types
}