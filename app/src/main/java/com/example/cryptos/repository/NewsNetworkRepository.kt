package com.example.cryptos.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.cryptos.interfaces.NetworkResponseCallback
import com.example.cryptos.network.RestClientNews
import com.example.cryptos.network.model.Article
import com.example.cryptos.network.model.ResponseNews

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class NewsNetworkRepository private constructor() {
    private lateinit var mCallback: NetworkResponseCallback
    private var mNewsList =
        MutableLiveData<List<Article>>().apply { value = emptyList() }

    companion object {
        private var mInstance: NewsNetworkRepository? = null
        fun getInstance(): NewsNetworkRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = NewsNetworkRepository()
                }
            }
            return mInstance!!
        }
    }

    private lateinit var mNewsCall: Call<ResponseNews>

    fun getNews(callback: NetworkResponseCallback, forceFetch : Boolean): MutableLiveData<List<Article>> {
        try{
        mCallback = callback
        if (!mNewsList.value!!.isEmpty() && !forceFetch) {
            mCallback.onNetworkSuccess()
            return mNewsList
        }
        mNewsCall = RestClientNews.getInstance().getApiService().getNews()
            mNewsCall.enqueue(object : Callback<ResponseNews> {
                override fun onResponse(call: Call<ResponseNews>, response: Response<ResponseNews>) {
                val news = response.body()
                    Log.e("asdasdsad",news.toString())
                    mNewsList.value = news?.articles
                mCallback.onNetworkSuccess()
            }

            override fun onFailure(call: Call<ResponseNews>, t: Throwable) {
                t.fillInStackTrace()
                mNewsList.value = emptyList()
                mCallback.onNetworkFailure(t)
            }

        })}catch (e:Exception){
            e.printStackTrace()
        }
        return mNewsList
    }
}