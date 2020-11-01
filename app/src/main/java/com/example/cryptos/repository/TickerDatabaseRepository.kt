package com.example.cryptos.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.cryptos.database.Ticker
import com.example.cryptos.database.TickerDao
import javax.inject.Inject

class TickerDatabaseRepository @Inject constructor(private val tickerDao: TickerDao) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(tickers: List<Ticker>) {
        tickerDao.insertAll(tickers)
    }

    fun getTickersByMarker(market: String): LiveData<List<Ticker>> {

        return tickerDao.getTickerByMarker(market)
    }


}
