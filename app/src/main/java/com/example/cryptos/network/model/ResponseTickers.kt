package com.example.cryptos.network.model

import com.example.cryptos.database.Ticker

data class ResponseTickers(
    val status : String,
    val data   :List<Ticker> = listOf()
)