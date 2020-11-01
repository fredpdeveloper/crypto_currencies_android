package com.example.cryptos.usecases

import com.example.cryptos.network.model.ResponseTickers
import com.example.cryptos.repository.CryptoRepository

class GetTickersUseCase(
    private val cryptoRepository: CryptoRepository
) {
    suspend fun invoke(): ResponseTickers {
        return cryptoRepository.getTickers()
    }
}