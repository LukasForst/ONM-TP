package onm.interfaces

import onm.configuration.PlaceType

/**
 * Place interface defines place in the room/garden etc. All future classes that represents place in real
 * world should implement this interface.
 * */
interface Place : HouseEntity {

    /**
     * Type of the place.
     * */
    val placeType: PlaceType

    /**
     * Short description of the place.
     * */
    val placeDescription: String?
}