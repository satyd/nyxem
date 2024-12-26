package com.levp.nyxem.data

import com.levp.nyxem.presentation.uistates.AbilityUiState

data class AbilityState(
    val numberOfAttacks: Int = 1,
    val levelImpale: Int = 0,
    val levelMindFlare: Int = 0,
    val levelVendetta: Int = 0,
    val levelDagon: Int = 0,
    val levelEthereal: Int = 0,
    val levelPhylactery: Int = 0,
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