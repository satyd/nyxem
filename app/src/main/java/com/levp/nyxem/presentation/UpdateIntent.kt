package com.levp.nyxem.presentation

import com.levp.nyxem.domain.constants.Abilities
import com.levp.nyxem.domain.constants.Properties

sealed class UpdateIntent {
    data class UpdateValue(val valueUpdate: ValueUpdate) : UpdateIntent()
    data class UpdateAbility(val isIncrease:Boolean, val ability: Abilities) : UpdateIntent()
    data class UpdateCounter(val isIncrease:Boolean, val property: Properties) : UpdateIntent()
    data object Error : UpdateIntent()
}