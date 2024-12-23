package com.levp.nyxem.presentation.uistates

import com.levp.nyxem.data.ValueState

data class ValueUiState(
    var attackDamage: String?,
    var targetMaxMP: String?,
    var targetMagResist: String?,
    var targetPhysResist: String?,
    var selfMagicAmplify: String?,
) {
    companion object {
        fun initialState(): ValueUiState = ValueUiState(
            attackDamage = "120",
            targetMaxMP = "500",
            targetMagResist = "25",
            targetPhysResist = "25",
            selfMagicAmplify = "0",

        )
    }
}

fun ValueUiState.toValueState():ValueState {
    return ValueState(
        attackDamage = attackDamage?.toIntOrNull() ?: 0,
        targetMaxMP = targetMaxMP?.toIntOrNull() ?: 0,
        targetMagResist = targetMagResist?.toIntOrNull() ?: 0,
        targetPhysResist = targetPhysResist?.toIntOrNull() ?: 0,
        selfMagicAmplify = selfMagicAmplify?.toIntOrNull() ?: 0
    )
}
