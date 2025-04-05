package com.hexagraph.pattagobhi.ui.screens.cardgeneration

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hexagraph.pattagobhi.Entity.Deck

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeckSelectionBottomSheet(
    sheetState: SheetState,
    decks: List<Deck>,
    onDeckSelected: (Deck) -> Unit,
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Select decks",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            decks.forEachIndexed { index, deck ->
                Text(
                    text = deck.name,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onDeckSelected(deck) }
                        .padding(vertical = 12.dp)
                )

                if (index != decks.lastIndex) {
                    HorizontalDivider(thickness = 1.dp)
                }
            }
        }
    }
}
