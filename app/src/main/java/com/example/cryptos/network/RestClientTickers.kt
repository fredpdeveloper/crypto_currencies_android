package com.example.cryptos.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


class RestClientTickers private constructor() {
    companion object {
        private const val BASE_URL = "https://api.cryptomkt.com/v1/"
        private lateinit var mApiTickers: ApiTickers
        private var mInstance: RestClientTickers? = null
        fun getInstance(): RestClientTickers {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = RestClientTickers()
                }
            }
            return mInstance!!
        }
    }

    init {
        val okHttpClient = OkHttpClient().newBuilder().connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        mApiTickers = retrofit.create(ApiTickers::class.java)
    }

    fun getApiService() = mApiTickers


}