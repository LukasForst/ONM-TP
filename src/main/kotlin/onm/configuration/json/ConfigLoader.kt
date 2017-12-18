package onm.configuration.json

import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.File

/**
 * Object responsible for parsing JSON to our data class.
 * */
object ConfigLoader {
    //todo this will not work for now, we need to find out how to save generics collection (like Map) to the json


    /**
     * Loads configuration class from given file path. Null is returned when parsing failed.
     * */
    fun loadConfigFromFile(path: String): ConfigurationDataClass? {
        return loadConfigFromString(File(path).readText())
    }

    /**
     * Loads configuration data class from given string. Null is returned when parsing failed.
     * */
    fun loadConfigFromString(data: String): ConfigurationDataClass {
        try {
            val config = Gson().fromJson<ConfigurationDataClass>(data)
            checkNameForUnique(config)
            return config;
        } catch (e: JsonSyntaxException) {
            return ConfigurationDataClass(listOf(), listOf(), listOf(), listOf())
        }
    }

    private fun checkNameForUnique(config: ConfigurationDataClass): Boolean {
        val desc = mutableSetOf<String>()

        for (i in config.rooms) {
            for (d in i.devices) {
                if (desc.contains(d.name)) {
                    throw Error("'${d.name}' is registered yet")
                } else {
                    desc.add(d.name)
                }
            }
            for (f in i.furniture) {
                if (desc.contains(f.name)) {
                    throw Error("name: '${f.name}' is registered yet")
                } else {
                    desc.add(f.name)
                }
            }
        }

        for (f in config.animals) {
            if (desc.contains(f.name)) {
                throw Error("name: '${f.name}' is registered yet")
            } else {
                desc.add(f.name)
            }
        }

        for (f in config.equipments) {
            if (desc.contains(f.name)) {
                throw Error("name: '${f.name}' is registered yet")
            } else {
                desc.add(f.name)
            }
        }

        for (f in config.vehicles) {
            if (desc.contains(f.name)) {
                throw Error("name: '${f.name}' is registered yet")
            } else {
                desc.add(f.name)
            }
        }

        return true
    }
}