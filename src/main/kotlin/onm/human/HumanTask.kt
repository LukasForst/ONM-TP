package onm.human

import onm.house.devices.AbstractDevice


class HumanTask(val type:TaskTypes) {

    var device: AbstractDevice? = null

    constructor(type: TaskTypes, device: AbstractDevice) : this(type) {
        this.device = device
    }

}
