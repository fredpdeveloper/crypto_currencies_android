package com.fpuente.mvvm_kotlin.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.cryptos.database.Ticker
import com.example.cryptos.interfaces.NetworkResponseCallback
import com.example.cryptos.network.RestClient
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import org.json.JSONObject

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class TickerNetworkRepository private constructor() {
    private lateinit var mCallback: NetworkResponseCallback
    private var mTickerList: MutableLiveData<List<Ticker>> =
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


    private lateinit var mTickerCall: Call<ResponseBody>

    fun getTickers(callback: NetworkResponseCallback, forceFetch : Boolean): MutableLiveData<List<Ticker>> {
        try{
        mCallback = callback
        if (!mTickerList.value!!.isEmpty() && !forceFetch) {
            mCallback.onNetworkSuccess()
            return mTickerList
        }
        mTickerCall = RestClient.getInstance().getApiService().getTickers()

        mTickerCall.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                val  responseBody = response.body()?.string()
                val jsonObject = JSONObject(responseBody)
                Log.e("JSON",jsonObject.toString())
                val gson = GsonBuilder().create()
                val tickers = gson.fromJson(jsonObject.getJSONArray("data").toString() , Array<Ticker>::class.java).toList()

                mTickerList.value = tickers
                mCallback.onNetworkSuccess()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("respomnseas",t.toString())

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