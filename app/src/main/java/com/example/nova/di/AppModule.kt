package com.example.nova.di

import com.example.nova.data.NovaRepository
import com.example.nova.security.CryptoManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCryptoManager(): CryptoManager {
        return CryptoManager()
    }

    @Provides
    @Singleton
    fun provideNovaRepository(cryptoManager: CryptoManager): NovaRepository {
        return NovaRepository(cryptoManager)
    }
}

