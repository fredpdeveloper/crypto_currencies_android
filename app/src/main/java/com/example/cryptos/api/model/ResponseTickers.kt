package com.example.cryptos.api.model

import com.example.cryptos.database.Ticker

data class ResponseTickers(
    val status : String,
    var data   :List<Ticker> = listOf()
)