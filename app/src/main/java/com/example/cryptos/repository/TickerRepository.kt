package com.example.cryptos.repository

import com.example.cryptos.api.CryptoService
import com.example.cryptos.api.model.ResponseTickers
import javax.inject.Inject

class TickerRepository @Inject constructor(private val service: CryptoService) {

    suspend fun getTickers(): ResponseTickers {
        return service.getTickers()
    }
}