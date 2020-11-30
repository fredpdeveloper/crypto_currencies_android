package com.example.cryptos.usecases

import com.example.cryptos.database.Ticker
import com.example.cryptos.repository.TickerRepository

class GetTickersUseCase(
    private val tickerRepository: TickerRepository
) {
    suspend fun invoke(market: String? = null): List<Ticker> {
        var response =
            tickerRepository.getTickers().data.filter { it.bid.toDouble() > 0 }
        response = if (market != null) {
            response.filter { it.market.contains(market) }

        } else {
            response
        }
        return response
    }
}