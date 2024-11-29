package com.levp.nyxem.data

import com.levp.nyxem.presentation.uistates.AbilityUiState

data class AbilityState(
    var numberOfAttacks: Int = 1,
    var levelImpale: Int = 0,
    var levelMindFlare: Int = 0,
    var levelVendetta: Int = 0,
    var levelDagon: Int = 0,
    var levelEthereal: Int = 0,
    var levelPhylactery: Int = 0,
)

fun AbilityState.toAbilityUiState(): AbilityUiState =
    AbilityUiState(
        numberOfAttacks = numberOfAttacks.toString(),
        levelImpale = levelImpale.toString(),
        levelMindFlare = levelMindFlare.toString(),
        levelVendetta = levelVendetta.toString(),
        levelDagon = levelDagon.toString(),
        levelEthereal = levelEthereal.toString(),
        levelPhylactery = levelPhylactery.toString(),
    )