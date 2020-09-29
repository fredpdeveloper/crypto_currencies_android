package com.example.cryptos.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cryptos.database.Ticker
import com.example.cryptos.interfaces.NetworkResponseCallback
import com.example.cryptos.network.model.Article
import com.example.cryptos.repository.NewsNetworkRepository
import com.example.cryptos.utils.NetworkHelper
import com.example.cryptos.repository.TickerNetworkRepository
import java.lang.Exception


class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private var repositoryNetwork = NewsNetworkRepository.getInstance()
    var mShowProgressBar: MutableLiveData<Boolean> = MutableLiveData()
    private var mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()
    private var mShowApiError: MutableLiveData<Boolean> = MutableLiveData()
    val allNews: LiveData<List<Article>>


    init {
        allNews = fetchNewsFromServer(application, true)

    }

    private fun fetchNewsFromServer(context: Context, forceFetch: Boolean): LiveData<List<Article>> {

        var mList: LiveData<List<Article>> =
            MutableLiveData<List<Article>>().apply { value = emptyList() }
        try {
            if (NetworkHelper.isOnline(context)) {
                mShowProgressBar.value = true
                mShowNetworkError.value = false
                mList = repositoryNetwork.getNews(object : NetworkResponseCallback {
                    override fun onNetworkFailure(th: Throwable) {
                        mShowApiError.value = true
                    }

                    override fun onNetworkSuccess() {
                        mShowProgressBar.value = false
                    }
                }, forceFetch)

            } else {
                mShowNetworkError.value = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return mList
    }
}
