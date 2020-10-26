package com.example.cryptos.repository

import com.example.cryptos.network.CryptoService
import com.example.cryptos.network.NewsService
import com.example.cryptos.network.model.ResponseNews
import com.example.cryptos.network.model.ResponseTickers
import javax.inject.Inject

class CryptoRepository @Inject constructor(private val service: CryptoService) {

    suspend fun getTickers(): ResponseTickers {
        return service.getTickers()
    }
}