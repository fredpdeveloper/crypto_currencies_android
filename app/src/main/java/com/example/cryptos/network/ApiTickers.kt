package com.example.cryptos.network


import com.example.cryptos.network.model.ResponseTickers
import retrofit2.Call
import retrofit2.http.GET

interface ApiTickers {

    @GET("ticker")
    fun getTickers(): Call<ResponseTickers>

}