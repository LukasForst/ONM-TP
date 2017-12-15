package onm.animals

/**
 * Animal type enum class represents type of animal.
 * */
enum class AnimalType(val pettingTimeInMillis: Long, val probabilityOfBrokingSomething: Double) {
    CAT(1000, 5.0),
    DOG(1000, 2.0),
    GOLF_FISH(0, 0.0),
    SPIDER(10, 0.0),
    SNAKE(500, 0.1)
}