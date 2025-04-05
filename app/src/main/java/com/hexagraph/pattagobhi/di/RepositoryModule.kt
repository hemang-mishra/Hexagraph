package com.hexagraph.pattagobhi.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hexagraph.pattagobhi.dao.DeckDao
import com.hexagraph.pattagobhi.repository.DeckRepository
import com.hexagraph.pattagobhi.repository.FirebaseBackupRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDeckRepository(deckDao: DeckDao): DeckRepository {
        return DeckRepository(deckDao)
    }

    @Provides
    @Singleton
    fun provideFirebaseBackupRepository(auth: FirebaseAuth, db: FirebaseFirestore): FirebaseBackupRepository {
        return FirebaseBackupRepository(auth, db)
    }
}