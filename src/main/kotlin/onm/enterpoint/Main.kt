package onm.enterpoint

import onm.builder.HouseBuilder
import onm.configuration.FurnitureType
import onm.configuration.RoomType
import onm.things.Food
import onm.things.FoodType


fun main(args: Array<String>){

        val houseBuilder = HouseBuilder()

        houseBuilder.addRoom(RoomType.TOILET)
        houseBuilder.addRoom(RoomType.LIVING_ROOM)
        houseBuilder.addRoom(RoomType.KITCHEN)

        val fridge = houseBuilder.addFridge(RoomType.KITCHEN)
        val washer = houseBuilder.addWasher(RoomType.TOILET)
    val oven = houseBuilder.addOven(RoomType.KITCHEN)


    houseBuilder.addFurniture(RoomType.TOILET, FurnitureType.CHAIR)


        fridge.addFood(Food(FoodType.APPLE))
    washer.startWashing(0.00001)
    oven.switchOn(listOf(Food(FoodType.APPLE)), 0.00001)

    }
