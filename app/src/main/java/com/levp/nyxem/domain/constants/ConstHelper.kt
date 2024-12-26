package com.levp.nyxem.domain.constants

enum class Abilities(val maxLevel: Int = 4) {
    Impale,
    MindFlare,
    Vendetta(3),
    Dagon(5),
    Attack(5),
    Phylactery(2),
    Ethereal(1)
}

enum class Properties(val minValue: Int = 0, val maxValue: Int = 100) {
    MagicRes,
    PhysicalRes
}

