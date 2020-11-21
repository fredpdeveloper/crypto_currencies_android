package com.example.cryptos.usecases

import com.example.cryptos.api.model.ResponseTickers
import com.example.cryptos.repository.TickerRepository

class GetTickersUseCase(
    private val tickerRepository: TickerRepository
) {
    suspend fun invoke(): ResponseTickers {
        return tickerRepository.getTickers()
    }
}