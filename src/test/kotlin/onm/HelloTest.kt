package onm

import org.junit.Test
import kotlin.test.assertEquals


internal class HelloTest {

    @Test
    fun run() {
        val hello = Hello().sayHello()
        assertEquals("Hello world!", hello)
    }
}