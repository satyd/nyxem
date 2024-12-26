package com.levp.nyxem.presentation

sealed class ValueUpdate(val value: String) {
    class UpdateDamage(dmg: String) : ValueUpdate(value = dmg)
    class UpdateMaxMana(maxMana: String) : ValueUpdate(value = maxMana)
    class UpdateMagResistance(magRes: String) : ValueUpdate(value = magRes)
    class UpdatePhysResistance(physRes: String) : ValueUpdate(value = physRes)
    class UpdateMagAmp(magAmp: String) : ValueUpdate(value = magAmp)

}