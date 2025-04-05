package com.hexagraph.pattagobhi.ui.screens.deck

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.fallback
import coil3.util.DebugLogger
import com.google.firebase.auth.FirebaseAuth
import com.hexagraph.pattagobhi.Entity.Deck
import com.hexagraph.pattagobhi.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeckScreen(
    viewModel: DeckScreenViewModel = hiltViewModel(),
    onRefreshClicked: ()-> Unit,
    onDeckClicked: (Int,String) -> Unit,
    onGenerateButtonClicked: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.getAllDeckUI()
    }
    val expanded = remember { mutableStateOf(false) }
    val state by viewModel.uiState.collectAsState()
    val showDialog = remember { mutableStateOf(false) }
    val deckName = remember { mutableStateOf("") }
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Create New Deck") },
            text = {
                TextField(
                    value = deckName.value,
                    onValueChange = { deckName.value = it },
                    placeholder = { Text("Enter deck name") }
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog.value = false
                        viewModel.addDesk(Deck(name = deckName.value)) // or pass to callback
                        deckName.value = ""
                    }
                ) {
                    Text("Create")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog.value = false }) {
                    Text("Cancel")
                }
            }
        )
    }
    val decks = state.decks
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MindStacks") },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Open Drawer */ }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Search */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = { onRefreshClicked() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                    IconButton(onClick = { /* TODO: More */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More")
                    }
                }
            )
        },
        floatingActionButton = {

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(end = 16.dp, bottom = 16.dp)
            ) {
                AnimatedVisibility(
                    visible = expanded.value,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    FloatingActionButton(
                        onClick = { showDialog.value = true },
                        containerColor = Color(0xFF2E2E2E),
                    ) {
                        Text(
                            "Create Deck", color = Color.White,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                    }
                }

                AnimatedVisibility(
                    visible = expanded.value,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    FloatingActionButton(
                        onClick = { onGenerateButtonClicked() },
                        containerColor = Color(0xFF2E2E2E),
                        modifier = Modifier.padding(horizontal = 4.dp)

                    ) {
                        Text(
                            "Generate Deck", color = Color.White,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        ) // e.g. Add Card
                    }
                }

                FloatingActionButton(
                    onClick = { expanded.value = !expanded.value },
                    containerColor = Color.White
                ) {
                    Icon(
                        imageVector = if (expanded.value) Icons.Default.Refresh else Icons.Default.Add,
                        contentDescription = "Toggle FAB"
                    )
                }
            }
        },
        containerColor = Color.Black
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color.Black)
                .clickable {
                    if (expanded.value) expanded.value = false
                }
        ) {
            item {
//                DashboardScreenTitle()
                decks.forEach { deck ->
                    DeckCard(deck = deck, onClick = {
                        onDeckClicked(deck.id,deck.name)
                    })
                }
            }
        }
    }

}

@Composable
fun DeckCard(deck: DeckUI, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(deck.name, color = Color.White)

            Row {
                Text(
                    deck.easyCount.toString(),
                    color = Color.Blue,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    deck.mediumCount.toString(),
                    color = Color.Red,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(deck.hardCount.toString(), color = Color.Green)
            }
        }
    }
}

@Composable
fun DashboardScreenTitle(
) {
    val auth = FirebaseAuth.getInstance().currentUser

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row {
            Log.d("Photo", auth?.photoUrl.toString())
//            if (auth?.photoUrl != null) {
//                AsyncImage(
//                    model = auth.photoUrl,
//                    contentDescription = null,
//                    modifier = Modifier
//                        .size(100.dp)
//                        .clip(CircleShape)
//                )
//
//            } else {
//                Icon(
//                    imageVector = Icons.Default.Person,
//                    contentDescription = null,
//                    modifier = Modifier
//                        .size(100.dp)
//                        .clip(CircleShape),
//                    tint = Color.Gray
//                )
//            }
            val context = LocalContext.current

            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(auth?.photoUrl)
                    .crossfade(true)
                    .fallback(R.drawable.assistanticon) // shown if photoUrl is null
                    .error(R.drawable.assistanticon)    // shown if loading fails
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    "Hello",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    fontSize = 16.sp
                )
                println(auth?.displayName ?: "User")
                Text(auth?.displayName ?: "User", fontSize = 20.sp, color = Color.White)
            }
        }
    }
}