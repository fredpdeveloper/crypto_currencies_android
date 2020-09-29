package com.example.cryptos.network


import com.example.cryptos.network.model.ResponseNews
import com.example.cryptos.network.model.ResponseTickers
import retrofit2.Call
import retrofit2.http.GET

interface ApiNews {

    @GET("everything?language=es&q=criptomonedas&sortBy=PublishedAt&apiKey=77b1c8b27ed54b08b828869c8bad3525")
    fun getNews(): Call<ResponseNews>

}