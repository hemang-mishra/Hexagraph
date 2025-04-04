package com.hexagraph.pattagobhi.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun DividerWithText(modifier: Modifier, text: String = "Or"){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .height(2.dp),
//            color = Color(0xFFADADAD)
        )
        Text(
            text = text,
            style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 20.sp,
//                fontFamily = bodyFontFamily,
                fontWeight = FontWeight(500),
//                color = Color(0xFFADADAD),
            ),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .height(2.dp),
//            color = Color(0xFFADADAD)
        )
    }
}