package onm.house.devices

import onm.TestUtils
import onm.events.WasherDoneIEvent
import onm.events.IEventHandler
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import java.util.*
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class WasherTest {
    lateinit var washer: Washer
    lateinit var IEventHandlerMock: IEventHandler
    lateinit var event: WasherDoneIEvent


    @Before
    fun setUp() {
        IEventHandlerMock = Mockito.mock(IEventHandler::class.java)
        washer = Washer(UUID.randomUUID(), IEventHandlerMock)
        event = WasherDoneIEvent(IEventHandlerMock)
    }

    @Test
    fun startWashingTest() {
        var washerCalled = false
        var timesChecked = 0
        `when`(IEventHandlerMock.handle(TestUtils.any<WasherDoneIEvent>())).then({
            washerCalled = true

            null
        })

        val washingTime = 0.001
        washer.startWashing(washingTime)

        assertTrue(washer.isBusy)
        while (timesChecked != 3 && !washerCalled) {
            Thread.sleep((washingTime * 60000).toLong())
            timesChecked++
        }

        assertFalse(washer.isBusy)
        verify(IEventHandlerMock, times(1)).handle(TestUtils.any<WasherDoneIEvent>())
    }

}