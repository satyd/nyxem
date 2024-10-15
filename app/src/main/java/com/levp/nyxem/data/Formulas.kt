package com.levp.nyxem.data

import com.levp.nyxem.data.constants.abDagon
import com.levp.nyxem.data.constants.abImpale
import com.levp.nyxem.data.constants.abMindFlare
import com.levp.nyxem.data.constants.abVendetta
import com.levp.nyxem.presentation.ValuesState

fun calculateDamage(ab:AbilitiesState, v: ValuesState): Double {
    val magResist = 1 - v.targetMagResist.toDouble() / 100
    val physResist = 1 - v.targetPhysResist.toDouble() / 100
    val numberOfHits = ab.numberOfAttacks
    val attackDamage = v.attackDamage.toInt()

    val vendettaDmg = abVendetta.damage[ab.levelVendetta]

    val impaleDamage = abImpale.damage[ab.levelImpale] * magResist
    val dagonDmg = abDagon.damage[ab.levelDagon] * magResist

    val abilityDmg =  vendettaDmg + impaleDamage + dagonDmg
    val attackDmg = numberOfHits * attackDamage * physResist

    //attack + ability damage with resistances
    val sumDmg = attackDmg + abilityDmg

    val flareManaDmg = (v.targetMaxMP.toDouble() * abMindFlare.manaAsDamage[ab.levelMindFlare]) * magResist

    val flareBonus = if (ab.levelMindFlare > 0) {
        (sumDmg) * abMindFlare.bonusDamage * magResist
    } else {
        0.0
    }
    //Log.d("hehe","attack dmg = $attackDmg, flareBonus = $flareBonus, flareManaDmg = $flareManaDmg")
    return sumDmg + flareBonus + flareManaDmg
}