package onm.house.devices

import onm.events.EventFactory
import onm.events.FridgeEmptyEvent
import onm.interfaces.EventHandler
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
    lateinit var fridge: Fridge
    lateinit var eventHandlerMock: EventHandler
    lateinit var event: FridgeEmptyEvent

    @Before
    fun setUp() {
        eventHandlerMock = mock(EventHandler::class.java)
        fridge = Fridge(UUID.randomUUID(), eventHandlerMock)
        event = EventFactory.createFridgeEmptyEvent(eventHandlerMock)
    }

    @Test
    fun createEventTest() {
        val food = fridge.food
        assertEquals(0, food.size)
        verify(eventHandlerMock, times(1)).handle(event)
    }

    @Test
    fun getFoodTest() {
        fridge.addFood(Food(FoodType.APPLE))
        fridge.addFood(Food(FoodType.BREAD))

        val food = fridge.food
        assertEquals(2, food.size)
    }
}