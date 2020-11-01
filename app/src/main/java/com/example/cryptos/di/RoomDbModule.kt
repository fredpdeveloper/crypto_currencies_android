package com.example.cryptos.di

import android.content.Context
import com.example.cryptos.database.TickerDao
import com.example.cryptos.database.TickerRoomDatabase
import com.example.cryptos.repository.TickerDatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ApplicationComponent::class)
@Module
object RoomDbModule {

    @Provides
    fun provideStudentDao(@ApplicationContext appContext: Context) : TickerDao{
        return TickerRoomDatabase.getDatabase(appContext).tickerDao()
    }

    @Provides
    fun provideTickerDBRepository(tickerDao: TickerDao) = TickerDatabaseRepository(tickerDao)



}