package com.levp.nyxem.presentation.item_rows

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.levp.nyxem.presentation.UpdateIntent
import com.levp.nyxem.presentation.ValueUpdate
import com.levp.nyxem.presentation.uistates.ValueUiState
import com.levp.nyxem.ui.ValueField

@Composable
fun TargetMagResRow(
    valueState: ValueUiState,
    onValueChange: (TextFieldValue) -> Unit
){
    ValueField(
        name = "Mag Res ",
        value = valueState.targetMagResist,
        isPercent = true,
        modifier = Modifier,
        onValueChange = onValueChange
    )
}