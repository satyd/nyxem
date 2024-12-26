package com.levp.nyxem.presentation.item_rows

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.levp.nyxem.R
import com.levp.nyxem.domain.constants.Abilities
import com.levp.nyxem.presentation.uistates.AbilityUiState
import com.levp.nyxem.ui.AbilityElement

@Composable
fun ItemRow(
    abilityState: AbilityUiState,
    updateAbilityValue: (Boolean, Abilities) -> Unit,
    modifier: Modifier = Modifier
){
    val dagonLvl = abilityState.levelDagon.toIntOrNull() ?: 0
    val phylacteryLvl = abilityState.levelPhylactery.toIntOrNull() ?: 0
    val dagonDrawableList = listOf<Int>(
        R.drawable.ic_dagon_1,
        R.drawable.ic_dagon_1,
        R.drawable.ic_dagon_2,
        R.drawable.ic_dagon_3,
        R.drawable.ic_dagon_4,
        R.drawable.ic_dagon_5,
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        AbilityElement(
            drawableId = dagonDrawableList[dagonLvl],
            name = "Dagon",
            currentLevel = abilityState.levelDagon,
            ability = Abilities.Dagon,
            onClick = updateAbilityValue
        )
        AbilityElement(
            drawableId = R.drawable.ic_ethereal,
            name = "Ethereal",
            currentLevel = abilityState.levelEthereal,
            ability = Abilities.Ethereal,
            onClick = updateAbilityValue
        )
        val (phylacteryDrawable, phylacteryName) =
            if (phylacteryLvl <= 1) {
                R.drawable.ic_phylactery to "Phylactery"
            } else {
                R.drawable.ic_khanda to "Khanda"
            }
        AbilityElement(
            drawableId = phylacteryDrawable,
            name = phylacteryName,
            currentLevel = abilityState.levelPhylactery,
            ability = Abilities.Phylactery,
            onClick = updateAbilityValue
        )
    }
}