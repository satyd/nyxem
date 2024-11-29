package com.levp.nyxem.presentation.uistates

import com.levp.nyxem.data.AbilityState

data class AbilityUiState(
    var numberOfAttacks: String = "1",
    var levelImpale: String = "0",
    var levelMindFlare: String = "0",
    var levelVendetta: String = "0",
    var levelDagon: String = "0",
    var levelEthereal: String = "0",
    var levelPhylactery: String = "0",
)

fun AbilityUiState.toAbilityState(): AbilityState =
    AbilityState(
        numberOfAttacks = numberOfAttacks.toIntOrNull() ?: 0,
        levelImpale = levelImpale.toIntOrNull() ?: 0,
        levelMindFlare = levelMindFlare.toIntOrNull() ?: 0,
        levelVendetta = levelVendetta.toIntOrNull() ?: 0,
        levelDagon = levelDagon.toIntOrNull() ?: 0,
        levelEthereal = levelEthereal.toIntOrNull() ?: 0,
        levelPhylactery = levelPhylactery.toIntOrNull() ?: 0
    )