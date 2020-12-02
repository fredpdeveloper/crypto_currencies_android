package com.example.cryptos.data.api

import com.example.cryptos.BuildConfig
import com.example.cryptos.data.api.model.ResponseNews
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface NewsService {

    @GET("everything?language=es&q=criptomonedas&sortBy=PublishedAt&apiKey=77b1c8b27ed54b08b828869c8bad3525")
    suspend fun getNews(): ResponseNews

    companion object {
        private const val BASE_URL = BuildConfig.API_NEW

        fun create(): NewsService {

            val logger = HttpLoggingInterceptor().apply {
                level = Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build()


            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(NewsService::class.java)

        }
    }
}