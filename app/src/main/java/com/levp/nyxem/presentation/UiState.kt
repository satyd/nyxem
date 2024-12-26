package com.levp.nyxem.presentation

import com.levp.nyxem.domain.ValueError
import com.levp.nyxem.presentation.uistates.AbilityUiState
import com.levp.nyxem.presentation.uistates.ValueUiState

data class UiState(
    val abilityState: AbilityUiState,
    val valueState: ValueUiState,
    val currentDamage: String = "0",
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val listOfErrors: List<ValueError> = emptyList()
) {
    companion object {
        fun initState(): UiState = UiState(
            abilityState = AbilityUiState(),
            valueState = ValueUiState.initialState(),
            currentDamage = "0"
        )
    }
}
