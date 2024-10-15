package com.levp.nyxem.presentation

sealed class ValueUpdate {
    data class UpdateDamage(val dmg: String) : ValueUpdate()
    data class UpdateMaxMana(val maxMana: String) : ValueUpdate()
    data class UpdateMagResistance(val magRes: String) : ValueUpdate()
    data class UpdatePhysResistance(val physRes: String) : ValueUpdate()
    data class UpdateMagAmp(val magAmp: String) : ValueUpdate()

}