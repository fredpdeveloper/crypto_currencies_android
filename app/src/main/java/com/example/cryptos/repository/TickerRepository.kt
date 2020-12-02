package com.example.cryptos.repository

import com.example.cryptos.data.api.TickerService
import com.example.cryptos.data.api.model.ResponseTickers
import javax.inject.Inject

class TickerRepository @Inject constructor(private val service: TickerService) {

    suspend fun getTickers(): ResponseTickers {
        return service.getTickers()
    }
}