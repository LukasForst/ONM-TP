package onm.house.devices

import onm.TestUtils
import onm.events.IEventHandler
import onm.events.WasherDoneEvent
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import java.util.*
import kotlin.test.assertTrue

class WasherTest {
    lateinit var washer: Washer
    lateinit var eventHandlerMock: IEventHandler
    lateinit var event: WasherDoneEvent


    @Before
    fun setUp() {
        eventHandlerMock = Mockito.mock(IEventHandler::class.java)
        washer = Washer(UUID.randomUUID(), eventHandlerMock)
        event = WasherDoneEvent(eventHandlerMock)
    }

    @Test
    fun startWashingTest() {
        var washerCalled = false
        var timesChecked = 0
        `when`(eventHandlerMock.handle(TestUtils.any<WasherDoneEvent>())).then({
            washerCalled = true
            null
        })

        val washingTime = 0.001
        washer.startWashing(washingTime)
        while (timesChecked != 3 && !washerCalled) {
            Thread.sleep((washingTime * 60000).toLong())
            timesChecked++
        }

        assertTrue(washer.isDeviceAvailable)
        verify(eventHandlerMock, times(1)).handle(TestUtils.any<WasherDoneEvent>())
    }

}