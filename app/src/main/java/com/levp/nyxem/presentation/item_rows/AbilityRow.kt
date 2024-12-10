package com.levp.nyxem.presentation.item_rows

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.levp.nyxem.R
import com.levp.nyxem.data.constants.Abilities
import com.levp.nyxem.presentation.uistates.AbilityUiState
import com.levp.nyxem.ui.AbilityElement

@Composable
fun AbilityRow(
    abilityState: AbilityUiState,
    updateAbilityValue: (Boolean, Abilities) -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        AbilityElement(
            drawableId = R.drawable.ic_impale,
            name = "Impale",
            currentLevel = abilityState.levelImpale,
            ability = Abilities.Impale,
            onClick = updateAbilityValue
        )
        AbilityElement(
            drawableId = R.drawable.ic_mind_flare,
            name = "Mind Flare",
            currentLevel = abilityState.levelMindFlare,
            ability = Abilities.MindFlare,
            onClick = updateAbilityValue
        )
        AbilityElement(
            drawableId = R.drawable.ic_vendetta,
            name = "Vendetta",
            currentLevel = abilityState.levelVendetta,
            ability = Abilities.Vendetta,
            onClick = updateAbilityValue
        )
    }
}