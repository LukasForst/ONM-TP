package onm.interfaces

import java.util.*

/**
 * This interface should be implemented by every entity which will be in the house.
 * */
interface HouseEntity {

    /**
     * Primary ID of the entity which can be used as key in the dictionaries/maps or in the future
     * for the primary key in database.
     * */
    val id: UUID

    /**
     * Generates entity report of current state.
     * */
    fun generateReport(): String
}
