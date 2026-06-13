package com.example.quickbitelocal.di

import com.google.firebase.ai.GenerativeModel
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AIModule {

    @Provides
    @Singleton
    fun provideGenerativeModel(): GenerativeModel {
        // "gemini-1.5-flash" is a good balance for customer support
        // Using the new firebase-ai API
        return Firebase.ai(backend = GenerativeBackend.vertexAI())
            .generativeModel("gemini-1.5-flash")
    }
}
