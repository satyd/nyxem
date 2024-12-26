package com.levp.nyxem.presentation.item_rows

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import com.levp.nyxem.R
import com.levp.nyxem.presentation.uistates.ValueUiState
import com.levp.nyxem.ui.CounterValueField

@Composable
fun TargetMagResRow(
    valueState: ValueUiState,
    onValueChange: (TextFieldValue) -> Unit
){
    CounterValueField(
        name = stringResource(R.string.target_mag_res),
        value = valueState.targetMagResist,
        isPercent = true,
        modifier = Modifier,
        onValueChange = onValueChange
    )
}