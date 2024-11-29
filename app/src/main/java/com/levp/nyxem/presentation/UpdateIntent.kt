package com.levp.nyxem.presentation

import com.levp.nyxem.data.constants.Abilities

sealed class UpdateIntent {
    data class UpdateValue(val valueUpdate: ValueUpdate) : UpdateIntent()
    data class UpdateAbility(val isIncrease:Boolean, val ability: Abilities) : UpdateIntent()
    data object Error : UpdateIntent()
}