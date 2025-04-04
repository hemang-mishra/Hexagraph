package com.hexagraph.pattagobhi.di

import android.content.Context
import androidx.room.Room
//import com.hexagraph.pattagobhi.model.databases.PrimaryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//class DatabaseModule {
//
//    @Provides
//    @Singleton
//    fun providesFaceInfoDatabase(
//        @ApplicationContext context: Context
//    ): PrimaryDatabase = Room.databaseBuilder(
//        context,
//        PrimaryDatabase::class.java,
//        "primary_db"
//    )
//        .build()
//}

//    @Provides
//    @Singleton
//    fun provideFaceInfoDao(
//        database: PrimaryDatabase
//    ) = database.faceInfoDao()
