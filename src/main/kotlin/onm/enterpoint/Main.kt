package onm.enterpoint

import onm.builder.HouseBuilder
import onm.configuration.FurnitureType
import onm.configuration.RoomConfig
import onm.configuration.RoomType
import onm.things.Food
import onm.things.FoodType


fun main(args: Array<String>) {

    val houseBuilder = HouseBuilder.createEmptyHouseBuilder()

    houseBuilder.addOrGetRoom(RoomConfig(RoomType.TOILET, "toilet", 1))
    houseBuilder.addOrGetRoom(RoomConfig(RoomType.KITCHEN, "kitchen", 1))
    houseBuilder.addOrGetRoom(RoomConfig(RoomType.LIVING_ROOM, "chill room", 1))

    val oven = houseBuilder.addOven(RoomType.KITCHEN, "kitchen")
    val fridge = houseBuilder.addFridge(RoomType.KITCHEN, "kitchen")
    val washer = houseBuilder.addWasher(RoomType.TOILET, "toilet")


    houseBuilder.addFurniture(RoomType.LIVING_ROOM, FurnitureType.CHAIR, "chill room")

    fridge.addFood(Food(FoodType.APPLE))
    washer.startWashing(0.00001)
    oven.switchOn(listOf(Food(FoodType.APPLE)), 0.00001)
}
