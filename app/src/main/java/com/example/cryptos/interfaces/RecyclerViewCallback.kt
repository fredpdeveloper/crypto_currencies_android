package com.example.cryptos.interfaces

import com.example.cryptos.database.Ticker

interface RecyclerViewCallback {
    fun onRecycleViewItemClick(ticker: Ticker, position: Int)
}