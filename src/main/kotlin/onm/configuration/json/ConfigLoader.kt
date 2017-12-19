package onm.configuration.json

import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import onm.loggerFor
import java.io.File

/**
 * Object responsible for parsing JSON to our data class.
 * */
object ConfigLoader {
    private val log = loggerFor(ConfigLoader::class.java)

    /**
     * Loads configuration class from given file path. Null is returned when parsing failed.
     * */
    fun loadConfigFromFile(path: String): ConfigurationDataClass? {
        return loadConfigFromString(File(path).readText())
    }

    /**
     * Loads configuration data class from given string. Null is returned when parsing failed.
     * */
    fun loadConfigFromString(data: String): ConfigurationDataClass? {
        return try {
            val config = Gson().fromJson<ConfigurationDataClass>(data)
            checkNameForUnique(config)
        } catch (e: JsonSyntaxException) {
            log.error("JSON Parsing failed.", e)
            null
        }
    }

    private fun checkNameForUnique(config: ConfigurationDataClass): ConfigurationDataClass? {
        val set = mutableSetOf<String>()

        return if (!verifyNaming(config.animals.map { x -> x.name }, set, "Animals")
                || !verifyNaming(config.humans.map { x -> x.name }, set, "Humans")
                || !verifyNaming(config.rooms.map { x -> x.name }, set, "Rooms")) {
            null
        } else {
            config
        }
    }

    private fun verifyNaming(names: Collection<String>, set: MutableSet<String>, collectionName: String): Boolean {
        val errMsg = "There is non unique name in "

        return if (names.all { x -> set.add(x) }) {
            true
        } else {
            log.error(errMsg + collectionName + "!")
            false
        }
    }
}