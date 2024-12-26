package com.levp.nyxem.data

import com.levp.nyxem.presentation.uistates.ValueUiState

data class ValueState(
    val attackDamage: Int = 0,
    val targetMaxMP: Int = 0,
    val targetMagResist: Int = 0,
    val targetPhysResist: Int = 0,
    val spellAmp: Int = 0,
) {
    companion object {
        fun initialState(): ValueState =
            ValueState(
                attackDamage = 120,
                targetMaxMP = 500,
                targetMagResist = 25,
                targetPhysResist = 25,
                spellAmp = 0,
                )
    }
}

fun ValueState.toUiState(): ValueUiState =
    ValueUiState(
        attackDamage = attackDamage.toString(),
        targetMaxMP = targetMaxMP.toString(),
        targetMagResist = targetMagResist.toString(),
        targetPhysResist = targetPhysResist.toString(),
        spellAmp = spellAmp.toString()
    )

