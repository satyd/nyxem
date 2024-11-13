package com.levp.nyxem.data

import com.levp.nyxem.presentation.uistates.ValueUiState

data class ValueState(
    var attackDamage: Int?,
    var targetMaxMP: Int?,
    var targetMagResist: Int?,
    var targetPhysResist: Int?,
    var selfMagicAmplify: Int?,
) {
    companion object {
        fun initialState(): ValueState = ValueState(
            attackDamage = 120,
            targetMaxMP = 500,
            targetMagResist = 25,
            targetPhysResist = 25,
            selfMagicAmplify = 0,

        )
    }
}

fun ValueState.toUiState() : ValueUiState =
    ValueUiState(
        attackDamage = attackDamage.toString(),
        targetMaxMP = targetMaxMP.toString(),
        targetMagResist = targetMagResist.toString(),
        targetPhysResist = targetPhysResist.toString(),
        selfMagicAmplify = selfMagicAmplify.toString()
    )

