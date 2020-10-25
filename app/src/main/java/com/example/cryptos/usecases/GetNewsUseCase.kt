package com.example.cryptos.usecases

import com.example.cryptos.network.model.ResponseNews
import com.example.cryptos.repository.NewsRepository

class GetNewsUseCase(
    private val newsRepository: NewsRepository
) {
    suspend fun invoke(): ResponseNews {
        return newsRepository.getNews()
    }
}