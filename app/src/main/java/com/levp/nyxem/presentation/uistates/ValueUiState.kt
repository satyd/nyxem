package com.levp.nyxem.presentation.uistates

import com.levp.nyxem.data.ValueState

data class ValueUiState(
    var attackDamage: String?,
    var targetMaxMana: String?,
    var targetMagResist: String?,
    var targetPhysResist: String?,
    var spellAmp: String?,
) {
    companion object {
        fun initialState(): ValueUiState = ValueUiState(
            attackDamage = "120",
            targetMaxMana = "500",
            targetMagResist = "30",
            targetPhysResist = "30",
            spellAmp = "0",

        )
    }
}

fun ValueUiState.toValueState():ValueState {
    return ValueState(
        attackDamage = attackDamage?.toIntOrNull() ?: 0,
        targetMaxMana = targetMaxMana?.toIntOrNull() ?: 0,
        targetMagResist = targetMagResist?.toIntOrNull() ?: 0,
        targetPhysResist = targetPhysResist?.toIntOrNull() ?: 0,
        spellAmp = spellAmp?.toIntOrNull() ?: 0
    )
}
