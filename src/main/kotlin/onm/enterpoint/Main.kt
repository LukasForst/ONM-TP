package onm.enterpoint

import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import onm.builder.HouseBuilder
import onm.configuration.json.ConfigLoader
import onm.configuration.json.ConfigurationDataClass
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Paths


fun main(args: Array<String>) {
    //TODO
    var configstr = ""
    if (args.isNotEmpty()) {
        try {
            val f = File(args[0])
            configstr = f.readText()
        } catch (err: FileNotFoundException) {
            return
        }

    } else {
        val path = System.getProperty("user.dir")
        configstr = Paths.get(path, "src", "main", "kotlin", "onm", "enterpoint", "config.json").toFile().readText()
    }
    try {
        println(configstr)
        val config = ConfigLoader.loadConfigFromString(configstr)
        val house = HouseBuilder.buildHouseFromConfig(config)

    }catch (err:Error){
        println(err)
    }






}
