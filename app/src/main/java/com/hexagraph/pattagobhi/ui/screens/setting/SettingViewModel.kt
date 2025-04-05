package com.hexagraph.pattagobhi.ui.screens.setting

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hexagraph.pattagobhi.dao.DeckDao
import com.hexagraph.pattagobhi.repository.FirebaseBackupRepository
import com.hexagraph.pattagobhi.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val backupRepository: FirebaseBackupRepository,
    private val dao: DeckDao
) : ViewModel() {
    var isBackUpRestoreInProgress = mutableStateOf(false)
    val status = mutableStateOf(Status.NOT_STARTED)

    fun backUp(onCompletion: ()-> Unit) {
        viewModelScope.launch {
            isBackUpRestoreInProgress.value = true
            val decks = dao.getAllDeck().first()
            val cards = dao.getAllCards().first()
            decks.forEach { deck ->
                backupRepository.backupDeck(deck) { success, error ->

                    if (success) {
                        // Handle success
                    } else {
                        // Handle error
                        Log.e("SettingViewModel", "Error backing up deck: $error")
                        return@backupDeck
                    }
                }
            }
            cards.forEach {
                backupRepository.backupCard(it) { success, error ->

                    onCompletion()
                    if (success) {
                        // Handle success
                    } else {
                        Log.e("SettingViewModel", "Error backing up card: $error")
                        return@backupCard
                    }
                }
            }
            isBackUpRestoreInProgress.value = false
        }
    }

    fun restoreData(onCompletion: () -> Unit) {
        viewModelScope.launch {

            try {
                isBackUpRestoreInProgress.value = true
                // Fetch decks and cards from the cloud
                val decks = backupRepository.fetchDecks()
                val cards = backupRepository.fetchCards()

                // Delete all local decks and cards
                dao.deleteAllDecks()
                dao.deleteAllCards()

                // Insert fetched decks and cards into the local database
                dao.insertCards(cards)
                decks.forEach { deck ->
                    dao.insertDesk(deck)
                }
                isBackUpRestoreInProgress.value = false
                onCompletion()
                status.value = Status.COMPLETED
            } catch (e: Exception) {
                Log.e("SettingViewModel", "Error restoring data: ${e.message}")
                status.value = Status.ERROR
                onCompletion()
                isBackUpRestoreInProgress.value = false
            }
        }
    }
}