package com.example.cryptos.network


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface ApiServices {

    @GET("ticker")
    fun getTickers(): Call<ResponseBody>

}