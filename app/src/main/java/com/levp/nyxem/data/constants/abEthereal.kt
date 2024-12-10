package com.levp.nyxem.data.constants

object abEthereal {
    private const val DAMAGE_TO_STATS = 1.415

    val damageMultiplier: Double = 1.4

    fun instantDamage(dmg: Int): Double {
        return (50.0 + dmg * 1.5 * DAMAGE_TO_STATS)
    }
}
