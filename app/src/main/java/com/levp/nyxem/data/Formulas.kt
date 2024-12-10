package com.levp.nyxem.data

import android.util.Log
import com.levp.nyxem.data.constants.Abilities
import com.levp.nyxem.data.constants.abDagon
import com.levp.nyxem.data.constants.abEthereal
import com.levp.nyxem.data.constants.abImpale
import com.levp.nyxem.data.constants.abMindFlare
import com.levp.nyxem.data.constants.abPhylactery
import com.levp.nyxem.data.constants.abVendetta

fun calculateDamage(ab: AbilityState, v: ValueState): Double {
    val spellAmp = (v.spellAmp ?: 0) / 100
    val magDealing = 1 - (v.targetMagResist?.toDouble() ?: 0.0) / 100 + spellAmp
    val physDealing = 1 - (v.targetPhysResist?.toDouble() ?: 0.0) / 100
    val numberOfHits = ab.numberOfAttacks
    val attackDamage = v.attackDamage?.toInt() ?: 0

    val vendettaDmg = abVendetta.damage[ab.levelVendetta] * (1 + spellAmp)

    val impaleDamage = abImpale.damage[ab.levelImpale] * magDealing
    val dagonDmg = abDagon.damage[ab.levelDagon] * magDealing
    val phylacteryDmg = abPhylactery.damage[ab.levelPhylactery] * magDealing
    val khandaDamage =
        if(ab.levelPhylactery == Abilities.Phylactery.maxLevel) {
            (v.attackDamage ?: 0) * 0.6 * magDealing
        } else {
            0.0
        }
    val isEthereal = ab.levelEthereal > 0

    //sum magic damage
    val magicDamage =
        if (isEthereal) {
            (dagonDmg + phylacteryDmg + khandaDamage) * 1.4
            + abEthereal.instantDamage(v.attackDamage?:0) * magDealing
        } else {
            dagonDmg + phylacteryDmg + khandaDamage
        }

    val abilityDmg = vendettaDmg + impaleDamage + magicDamage
    val attackDmg = numberOfHits * attackDamage * physDealing

    //attack + ability damage with resistances
    val sumDmg = attackDmg + abilityDmg

    val flareManaDmg = ((v.targetMaxMP?.toDouble()
        ?: 0.0) * abMindFlare.manaAsDamage[ab.levelMindFlare]) * magDealing

    val flareBonus = if (ab.levelMindFlare > 0) {
        (sumDmg) * abMindFlare.bonusDamage * magDealing
    } else {
        0.0
    }

    val flareResultingDmg = if(isEthereal) {
        (flareBonus + flareManaDmg) * abEthereal.damageMultiplier
    } else {
        flareBonus + flareManaDmg
    }
    Log.d("hehe","" +
            "attack dmg = $attackDmg, " +
            "flareDmg = $flareResultingDmg, " +
            "vendetta = $vendettaDmg, " +
            "dagon = $dagonDmg")
    return sumDmg + flareResultingDmg
}