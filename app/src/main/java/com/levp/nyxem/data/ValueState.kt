package com.levp.nyxem.data

import com.levp.nyxem.presentation.uistates.ValueUiState

data class ValueState(
    val attackDamage: Int = 0,
    val targetMaxMana: Int = 0,
    val targetMagResist: Int = 0,
    val targetPhysResist: Int = 0,
    val spellAmp: Int = 0,
) {
    companion object {
        fun initialState(): ValueState =
            ValueState(
                attackDamage = 120,
                targetMaxMana = 500,
                targetMagResist = 25,
                targetPhysResist = 25,
                spellAmp = 0,
                )
    }
}

fun ValueState.toUiState(): ValueUiState =
    ValueUiState(
        attackDamage = attackDamage.toString(),
        targetMaxMana = targetMaxMana.toString(),
        targetMagResist = targetMagResist.toString(),
        targetPhysResist = targetPhysResist.toString(),
        spellAmp = spellAmp.toString()
    )

