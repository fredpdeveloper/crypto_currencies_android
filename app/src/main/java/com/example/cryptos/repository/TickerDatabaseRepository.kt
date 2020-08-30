
package com.example.cryptos.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.cryptos.database.Ticker
import com.example.cryptos.database.TickerDao

class TickerDatabaseRepository(private val tickerDao: TickerDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allTickers: LiveData<List<Ticker>> = tickerDao.getAllTickers()

    // You must call this on a non-UI thread or your app will crash. So we're making this a
    // suspend function so the caller methods know this.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(tickers: List<Ticker>) {
        tickerDao.insertAll(tickers)
    }

    fun getTickersByMarker(market:String): LiveData<List<Ticker>> {

        return tickerDao.getTickerByMarker(market)
    }


}
