package com.levp.nyxem.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResultPart(
    isError: Boolean,
    isLoading: Boolean,
    totalDamageStr: String = "0.0"
) {
    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!isError) {
            Text(
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                text = "Total damage: "
            )
            Box(
                modifier = Modifier.fillMaxHeight().width(96.dp),
                contentAlignment = Alignment.Center
            ) {
                if (!isLoading) {
                    Text(
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        text = totalDamageStr
                    )
                } else {
                    CircularProgressIndicator()
                }
            }
        } else {
            Text(
                text = "Check your input data",
                color = Color.Red
            )
        }
    }
}
