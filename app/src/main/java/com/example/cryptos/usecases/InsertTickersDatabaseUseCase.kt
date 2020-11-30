package com.example.cryptos.usecases

import com.example.cryptos.database.Ticker
import com.example.cryptos.repository.TickerDatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class InsertTickersDatabaseUseCase(
    private val tickerDatabaseRepository: TickerDatabaseRepository

) {
    suspend fun invoke(tickers: List<Ticker>){
        return withContext(Dispatchers.IO) { tickerDatabaseRepository.insert(tickers) }
    }
}