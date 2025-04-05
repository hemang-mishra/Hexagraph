package com.hexagraph.pattagobhi.ui.screens.setting

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hexagraph.pattagobhi.repository.DeckRepository
import com.hexagraph.pattagobhi.repository.FirebaseBackupRepository
import com.hexagraph.pattagobhi.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch

//@HiltViewModel
//class SettingViewModel @Inject constructor(val backupRepository: FirebaseBackupRepository, val deckRepository: DeckRepository):ViewModel() {
//val status = mutableStateOf(Status.NOT_STARTED)
//    fun backUp(){
//        viewModelScope.launch {
//            val decks = deckRepository.getAllDeck().last()
//            decks.forEach { deck->
//                backupRepository.backupDeck(deck){success, error->
//                    if(success){
//
//                    }
//                    else{
//                        return@backupDeck
//                    }
//
//                }
//            }
//        }
//    }
//}