package onm.configuration.json

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
    fun loadConfigFromString(data: String): ConfigurationDataClass? {
        return try {
            return Gson().fromJson(data, ConfigurationDataClass::class.java)
        } catch (e: JsonSyntaxException) {
            null
        }
    }
}