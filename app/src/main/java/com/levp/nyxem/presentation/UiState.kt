package com.levp.nyxem.presentation

import com.levp.nyxem.presentation.uistates.AbilityUiState
import com.levp.nyxem.presentation.uistates.ValueUiState

data class UiState(
    val abilityState: AbilityUiState,
    val valueState: ValueUiState
)
