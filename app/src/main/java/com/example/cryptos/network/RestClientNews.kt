package com.example.cryptos.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


class RestClientNews private constructor() {
    companion object {
        private const val BASE_URL =
            "https://newsapi.org/v2/"
        private lateinit var mApiNews: ApiNews
        private var mInstance: RestClientNews? = null
        fun getInstance(): RestClientNews {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = RestClientNews()
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
        mApiNews = retrofit.create(ApiNews::class.java)
    }

    fun getApiService() = mApiNews


}