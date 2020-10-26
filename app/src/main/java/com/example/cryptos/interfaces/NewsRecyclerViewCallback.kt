package com.example.cryptos.interfaces

import com.example.cryptos.network.model.Article

interface NewsRecyclerViewCallback {
    fun onRecycleViewItemClick(article: Article, position: Int)
}