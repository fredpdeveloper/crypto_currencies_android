package com.example.cryptos.data.api.model

import com.example.cryptos.data.database.Ticker

data class ResponseTickers(
    val status: String,
    var data: List<Ticker> = listOf()
)