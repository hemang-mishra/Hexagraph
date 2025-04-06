package com.hexagraph.pattagobhi.ui.screens.cardgeneration

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hexagraph.pattagobhi.ui.components.LottieAnimationComposable
import dev.jeziellago.compose.markdowntext.MarkdownText
import com.hexagraph.pattagobhi.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackBottomSheet(
    sheetState: SheetState,
    response: String?,
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = "Feedback",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            if(response  != null)
            MarkdownText(markdown = response,
                syntaxHighlightColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                syntaxHighlightTextColor = MaterialTheme.colorScheme.onPrimaryContainer)
            else{
                Box(modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center) {
                    LottieAnimationComposable(
                        resource = R.raw.new_loading,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}
