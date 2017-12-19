package onm.enterpoint

import onm.api.OvenControlApi
import onm.builder.HouseBuilder
import onm.configuration.json.ConfigLoader
import onm.things.Food
import onm.things.FoodType
import java.nio.file.Paths


fun main(args: Array<String>) {
    val configstr = Paths.get(System.getProperty("user.dir"), "src", "main", "kotlin", "onm", "enterpoint", "config.json").toFile().readText()
    val configs = ConfigLoader.loadConfigFromString(configstr)
    if (configs == null) {
        print("Error while parsing")
        return
    }

    val house = HouseBuilder.buildHouseFromConfig(configs)

    val oven = house.getControlApiByUUID<OvenControlApi>(house.getUidOfDevice("Oven1"))
            ?.switchOn(listOf(Food(FoodType.BREAD)), 1.0)

    return
}
