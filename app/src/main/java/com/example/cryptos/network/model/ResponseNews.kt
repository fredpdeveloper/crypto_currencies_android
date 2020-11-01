package com.example.cryptos.network.model


data class ResponseNews(
    val status: String?,
    val totalResults: Int?,
    val articles: List<Article>?
)