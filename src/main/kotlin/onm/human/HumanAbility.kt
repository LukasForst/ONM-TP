package onm.human

enum class HumanAbility(val ability: Int):Comparable<HumanAbility> {
    CAN_COOK(1),
    CAN_REPAIR_DEVICES(2),
    ANY(3);

}