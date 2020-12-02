package com.example.cryptos.repository

import com.example.cryptos.data.api.NewsService
import com.example.cryptos.data.api.model.ResponseNews
import javax.inject.Inject

class NewsRepository @Inject constructor(private val service: NewsService) {

    suspend fun getNews(): ResponseNews {
        return service.getNews()
    }
}