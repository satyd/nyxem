package com.levp.nyxem.domain

data class Range(val min: Int, val max: Int) {
    fun isInRange(value: Int) = value in min..max
}

enum class AllowedValues(val property: String, val range: Range) {
    AttackDamage("AttackDamage", Range(0, 3000)),
    MaxMana("MaxMana", Range(0, 8000)),
    MagicAmplify("MagicAmplify", Range(0, 99)),
    MagicResistance("MagicResistance", Range(0, 99)),
    PhysicalResistance("PhysicalResistance", Range(0, 99)),
}