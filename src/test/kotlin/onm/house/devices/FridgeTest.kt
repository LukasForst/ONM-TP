package onm.house.devices

import onm.TestUtils
import onm.api.FridgeControlApi
import onm.configuration.DeviceType
import onm.configuration.RoomType
import onm.configuration.json.DeviceConfig
import onm.configuration.json.PowerConsumption
import onm.events.FridgeEmptyEvent
import onm.events.IEventHandler
import onm.events.isFinishedEvent
import onm.house.places.Room
import onm.things.Food
import onm.things.FoodType
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import java.util.*
import kotlin.test.assertEquals

/**
 * @date 12/5/17
 * @author Lukas Forst
 */
class FridgeTest {
    lateinit var fridge: FridgeControlApi
    lateinit var eventHandlerMock: IEventHandler


    @Before
    fun setUp() {
        val room = Room(UUID.randomUUID(), "kitchen", RoomType.LIVING_ROOM, 1)
        eventHandlerMock = mock(IEventHandler::class.java)
        fridge = Fridge(UUID.randomUUID(), eventHandlerMock, DeviceConfig(DeviceType.FRIDGE, "fridge",
                PowerConsumption()), room).fridgeControlApi
    }

    @Test
    fun createEventTest() {
        val food = fridge.getFood()
        assertEquals(0, food.size)
        Thread.sleep(50)
        verify(eventHandlerMock, times(1)).handle(TestUtils.any<FridgeEmptyEvent>())
    }

    @Test
    fun getFoodTest() {
        fridge.addFood(Food(FoodType.APPLE))
        fridge.addFood(Food(FoodType.BREAD))

        val food = fridge.getFood()
        assertEquals(2, food.size)
        verify(eventHandlerMock, never()).handle(TestUtils.any<isFinishedEvent>())
    }
}