package onm.enterpoint

import onm.things.Food
import onm.things.FoodType


fun main(args: Array<String>) {
    //TODO
//    var configstr = ""
//    if (args.isNotEmpty()) {
//        try {
//            val f = File(args[0])
//            configstr = f.readText()
//        } catch (err: FileNotFoundException) {
//            return
//        }
//
//    } else {
//        val path = System.getProperty("user.dir")
//        configstr = Paths.get(path, "src", "main", "kotlin", "onm", "enterpoint", "config.json").toFile().readText()
//    }
//    try {
//        println(configstr)
//        val config = ConfigLoader.loadConfigFromString(configstr)
//        val house = HouseBuilder.buildHouseFromConfig(config)
//
//    }catch (err:Error){
//        println(err)
//    }

    val types = FoodType.values()

    val ret = types.map { Food(it) }
    print(ret)



}
