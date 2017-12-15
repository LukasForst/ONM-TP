package onm.house.devices

import onm.TestUtils
import onm.configuration.DeviceType
import onm.configuration.json.DeviceConfig
import onm.configuration.json.PowerConsumption
import onm.events.BakeFinishedEvent
import onm.events.IEventHandler
import onm.things.Food
import onm.things.FoodType
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import java.util.*


class OvenTest {

    lateinit var oven : Oven
    lateinit var eventHandlerMock: IEventHandler

    @Before
    fun setUp(){
        eventHandlerMock = mock(IEventHandler::class.java)
        oven = Oven(UUID.randomUUID(), eventHandlerMock, DeviceConfig(DeviceType.OVEN, PowerConsumption()))
    }

    @Test
    fun createEventTest(){
        val bakingTime = 0.0001
        oven.switchOn(listOf(Food(FoodType.BREAD)), bakingTime)
        Thread.sleep((bakingTime * 60000 + 50).toLong()) //todo redo for more thread safety
        verify(eventHandlerMock, times(1)).handle(TestUtils.any<BakeFinishedEvent>())
    }
}