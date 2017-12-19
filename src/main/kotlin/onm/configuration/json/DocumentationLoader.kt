package onm.configuration.json

import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import onm.configuration.DeviceType
import java.nio.file.Paths

/**
 * Loads documentation data.
 * */
object DocumentationLoader {

    /**
     * Retrieves documentation for given device, otherwise returns null.
     * */
    fun loadConfigFor(type: DeviceType): String? {
        val allData = Paths.get(System.getProperty("user.dir"), "src", "main", "kotlin", "onm", "configuration", "json", "device_docs.json").toFile().readText()
        val jsonDoc = Gson().fromJson<DeviceDocumentationJson>(allData)

        return jsonDoc.devices.singleOrNull { x -> x.type == type }?.documentation
    }
}

/**
 * Config which is saved as json.
 * */
data class DeviceDocumentationJson(val devices: Collection<DeviceDocumentation>)


/**
 * Single documentation data for device.
 * */
data class DeviceDocumentation(val type: DeviceType, val documentation: String)