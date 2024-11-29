package com.levp.nyxem.data.constants

enum class Abilities(val maxLevel: Int = 4) {
    Impale,
    MindFlare,
    Vendetta(3),
    Dagon(5),
    Attack(5),
    Phylactery(2),
    Ethereal(1)
}