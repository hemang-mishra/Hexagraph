package com.hexagraph.pattagobhi.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun HeadingOfCreateAccount(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = "Create Your Account",

            // heading large 24
            style = TextStyle(
                fontSize = 24.sp,
                lineHeight = 48.sp,
                fontWeight = FontWeight(600),
            )
        )
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = "Create account for planning your life",

            // content 16
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 21.sp,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Center,
            )
        )
    }
}