package onm.api

import onm.house.devices.Fridge

class FridgeDataApi(
        private val fridge : Fridge){

    fun getTotalConsumption() : Int{
        print(5)
        return 2
    }


}