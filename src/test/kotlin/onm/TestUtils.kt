package onm

import org.mockito.Mockito

object TestUtils {
    fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    @Suppress("UNCHECKED_CAST") //this is temporary workaround for mockito kotlin
    private fun <T> uninitialized(): T = null as T

}
