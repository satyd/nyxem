package com.levp.nyxem.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.levp.nyxem.data.constants.Abilities

@Composable
fun AbilityCounters(
    currentLevel: String = "0",
    ability: Abilities,
    onClick: (Boolean, Abilities) -> Unit
){
    Row(verticalAlignment = Alignment.CenterVertically) {
        CounterButton(text = "-") {
            onClick(false, ability)
        }
        Text(text = " $currentLevel ")
        CounterButton(text = "+") {
            onClick(true, ability)
        }
    }
}

@Composable
fun CounterButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .size(28.dp)
            .padding(2.dp)
        //.clip(shape = RoundedCornerShape(12.dp))
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.padding(0.dp),
                text = text,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Medium,
                    //fontFamily = FontFamily(Font(R.font.sf_pro_display)),
                    lineHeight = 21.sp
                )
            )
        }
    }
}

@Preview
@Composable
fun ButtonPreview() {
    CounterButton(text = "+") {

    }
}