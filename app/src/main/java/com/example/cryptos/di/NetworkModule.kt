package com.example.cryptos.di

import com.example.cryptos.data.api.TickerService
import com.example.cryptos.data.api.NewsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideNewsService(): NewsService{
        return NewsService.create()
    }

    @Singleton
    @Provides
    fun provideTickerService(): TickerService{
        return TickerService.create()
    }

}