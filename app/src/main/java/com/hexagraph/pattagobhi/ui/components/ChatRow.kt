package com.hexagraph.pattagobhi.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hexagraph.pattagobhi.R


@Composable
fun ChatRow(imageResId: Int = R.drawable.assistanticon, chatText: String, isAI: Boolean = false, isHtml: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .align(Alignment.Top)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Card(
            shape = RoundedCornerShape(0.dp, 16.dp, 16.dp, 16.dp),
            modifier = Modifier.fillMaxWidth(0.95f),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF5A6A79)
            )
        ) {
            if(!isHtml){
                Text(
                    text = chatText,
                    modifier = Modifier.padding(16.dp),
                    fontSize = if(!isAI) 16.sp else 13.sp,
                    color = Color.White)
            }
            else
            {
                Text(
                    text = AnnotatedString.fromHtml(chatText.trimIndent()),
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}
