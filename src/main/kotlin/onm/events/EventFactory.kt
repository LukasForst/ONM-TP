package onm.events

import onm.configuration.EventSeverity
import onm.interfaces.EventHandler

/**
 * Event factory is used for creating events without using constructors.
 * */
object EventFactory {
    private val fridgeEmptyMessage = "Fridge is empty!"
    private val washerDoneMessage = "Your clothes are washed!"

    /**
     * Creates @see FridgeEmptyEvent
     * */
    fun createFridgeEmptyEvent(eventHandler: EventHandler): FridgeEmptyEvent {
        return FridgeEmptyEvent(fridgeEmptyMessage, EventSeverity.INFO, eventHandler)
    }

    /**
     * Creates @see WasherDoneEvent
     * */
    fun createWasherDoneEvent(eventHandler: EventHandler): WasherDoneEvent {
        return WasherDoneEvent(washerDoneMessage, EventSeverity.EXECUTION_DONE, eventHandler)
    }
}