package com.levp.nyxem.presentation.item_rows

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.levp.nyxem.data.ValueState
import com.levp.nyxem.data.constants.Abilities
import com.levp.nyxem.presentation.UpdateIntent
import com.levp.nyxem.presentation.ValueUpdate
import com.levp.nyxem.presentation.uistates.AbilityUiState
import com.levp.nyxem.presentation.uistates.ValueUiState
import com.levp.nyxem.ui.AbilityCounters
import com.levp.nyxem.ui.ValueField

@Composable
fun AutoAttackRow(
    abilityState: AbilityUiState,
    valueState: ValueUiState,
    updateAbilityValue: (Boolean, Abilities) -> Unit,
    onValueChange: (TextFieldValue) -> Unit
){
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        ValueField(
            name = "Attack Damage",
            value = valueState.attackDamage,
            modifier = Modifier,
            onValueChange = onValueChange
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "Hits:")
        Spacer(modifier = Modifier.width(4.dp))
        AbilityCounters(
            currentLevel = abilityState.numberOfAttacks,
            ability = Abilities.Attack,
            onClick = updateAbilityValue
        )
    }
}