package com.hexagraph.pattagobhi.di

import com.google.firebase.auth.FirebaseAuth
import com.hexagraph.pattagobhi.service.AuthenticationService
import com.hexagraph.pattagobhi.service.GeminiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServicesModule {

//    @Binds
//    abstract fun bindFaceRecognitionService(
//        faceRecognitionServiceImpl: FaceRecognitionServiceImpl
//    ): FaceRecognitionService

    @Binds
    abstract fun bindGeminiService(
        geminiService: GeminiService
    ): GeminiService

}