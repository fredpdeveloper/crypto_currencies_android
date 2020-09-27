package com.example.cryptos.repository

import androidx.lifecycle.MutableLiveData
import com.example.cryptos.database.Ticker
import com.example.cryptos.interfaces.NetworkResponseCallback
import com.example.cryptos.network.RestClient
import com.example.cryptos.network.model.ResponseTickers

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class TickerNetworkRepository private constructor() {
    private lateinit var mCallback: NetworkResponseCallback
    private var mTickerList =
        MutableLiveData<List<Ticker>>().apply { value = emptyList() }

    companion object {
        private var mInstance: TickerNetworkRepository? = null
        fun getInstance(): TickerNetworkRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = TickerNetworkRepository()
                }
            }
            return mInstance!!
        }
    }

    private lateinit var mTickerCall: Call<ResponseTickers>

    fun getTickers(callback: NetworkResponseCallback, forceFetch : Boolean): MutableLiveData<List<Ticker>> {
        try{
        mCallback = callback
        if (!mTickerList.value!!.isEmpty() && !forceFetch) {
            mCallback.onNetworkSuccess()
            return mTickerList
        }
        mTickerCall = RestClient.getInstance().getApiService().getTickers()
            mTickerCall.enqueue(object : Callback<ResponseTickers> {
                override fun onResponse(call: Call<ResponseTickers>, response: Response<ResponseTickers>) {
                val tickers = response.body()
                    mTickerList.value = tickers?.data
                mCallback.onNetworkSuccess()
            }

            override fun onFailure(call: Call<ResponseTickers>, t: Throwable) {
                t.fillInStackTrace()
                mTickerList.value = emptyList()
                mCallback.onNetworkFailure(t)
            }

        })}catch (e:Exception){
            e.printStackTrace()
        }
        return mTickerList
    }
}