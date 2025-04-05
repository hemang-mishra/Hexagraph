package com.hexagraph.pattagobhi.di

import android.content.Context
import androidx.room.Room
import com.hexagraph.pattagobhi.dao.DeckDao
import com.hexagraph.pattagobhi.model.databases.AppDatabase
//import com.hexagraph.pattagobhi.model.databases.PrimaryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(SupervisorJob())

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        scope: CoroutineScope
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "flashcard-db"
        )
            .addCallback(AppDatabase.Callback(scope, context))
            .build()
    }

    @Provides
    fun provideDeskDao(db: AppDatabase): DeckDao = db.deskDao()
}
