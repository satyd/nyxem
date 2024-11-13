package com.levp.nyxem.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ValueField(
    name: String,
    value: String?,
    modifier: Modifier,
    isPercent: Boolean = false,
    onValueChange: (TextFieldValue) -> Unit
) {
    Row(modifier = Modifier.width(200.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(text = name)
        Spacer(modifier = Modifier.size(2.dp))
        BasicTextField(
            modifier = modifier
                .width(64.dp)
                .height(36.dp)
                //.align(Alignment.CenterVertically)
                .background(
                    shape = RoundedCornerShape(4.dp),
                    color = Color.LightGray
                )
                .padding(8.dp),
            textStyle = TextStyle(
                fontSize = 16.sp,
                lineHeight = 18.sp,
                textAlign = TextAlign.Center
            ),
            singleLine = true,
            value = TextFieldValue(
                text = value ?: "",
                selection = TextRange(value?.length ?: 0)
            ),
            onValueChange = onValueChange,
            /*{
            onValueChange(it.text, update)
            //viewModel.updateValue(valuesState.value, ValueUpdate.UpdateDamage(it.text))
            //textFieldValueState = it
        }*/
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        if (isPercent) {
            Spacer(modifier = Modifier.size(2.dp))
            Text(text = "%")
        }
    }
}