package onm.house.devices

import onm.TestUtils
import onm.events.BakeFinishedEvent
import onm.interfaces.EventHandler
import onm.things.Food
import onm.things.FoodType
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import java.util.*


class OvenTest(){

    lateinit var oven : Oven
    lateinit var eventHandlerMock : EventHandler

    @Before
    fun setUp(){
        eventHandlerMock = mock(EventHandler::class.java)
        oven = Oven(UUID.randomUUID(), eventHandlerMock)
    }

    @Test
    fun createEventTest(){
        oven.switchOn(listOf(Food(FoodType.BREAD)), 1) //TODO REDO when oven bakes in another thread
        verify(eventHandlerMock, times(1)).handle(TestUtils.any<BakeFinishedEvent>())
    }
}