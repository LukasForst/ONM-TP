package onm.house.devices

import onm.TestUtils
import onm.configuration.DeviceType
import onm.configuration.RoomType
import onm.configuration.json.DeviceConfig
import onm.configuration.json.PowerConsumption
import onm.events.DeviceFinishedEvent
import onm.events.IEventHandler
import onm.house.places.Room
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import java.util.*
import kotlin.test.assertTrue

class WasherTest {
    lateinit var washer: Washer
    lateinit var eventHandlerMock: IEventHandler
    lateinit var event: DeviceFinishedEvent


    @Before
    fun setUp() {
        val room = Room(UUID.randomUUID(), "kitchen", RoomType.LIVING_ROOM, 1)
        eventHandlerMock = Mockito.mock(IEventHandler::class.java)
        washer = Washer(UUID.randomUUID(), eventHandlerMock, DeviceConfig(DeviceType.WASHER, "washer",
                PowerConsumption()), room)
        event = DeviceFinishedEvent(eventHandlerMock, UUID.randomUUID(), "something")
    }

    @Test
    fun startWashingTest() {
        var washerCalled = false
        var timesChecked = 0
        `when`(eventHandlerMock.handle(TestUtils.any<DeviceFinishedEvent>())).then({
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
        verify(eventHandlerMock, times(1)).handle(TestUtils.any<DeviceFinishedEvent>())
    }

}