package com.example.cryptos.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cryptos.database.Ticker
import com.example.cryptos.database.TickerRoomDatabase
import com.example.cryptos.interfaces.NetworkResponseCallback
import com.example.cryptos.repository.TickerDatabaseRepository
import com.example.cryptos.utils.NetworkHelper
import com.fpuente.mvvm_kotlin.repository.TickerNetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


class TickersViewModel(application: Application) : AndroidViewModel(application) {
    private val repositoryDatabase: TickerDatabaseRepository
    private var repositoryNetwork = TickerNetworkRepository.getInstance()
    var mShowProgressBar: MutableLiveData<Boolean> = MutableLiveData()
    private var mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()
    private var mShowApiError: MutableLiveData<Boolean> = MutableLiveData()
    val allTickers: LiveData<List<Ticker>>


    init {
        val tickerDao = TickerRoomDatabase.getDatabase(
            application,
            viewModelScope
        ).tickerDao()
        repositoryDatabase =
            TickerDatabaseRepository(tickerDao)
        allTickers = fetchTickersFromServer(application, true)

    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(tickers: List<Ticker>) = viewModelScope.launch(Dispatchers.IO) {
        repositoryDatabase.insert(tickers)
    }

    fun getTickersByMarker(marker: String): LiveData<List<Ticker>> {
        return repositoryDatabase.getTickersByMarker(marker)
    }

    fun fetchTickersFromServer(context: Context, forceFetch: Boolean): LiveData<List<Ticker>> {

        var mList: LiveData<List<Ticker>> =
            MutableLiveData<List<Ticker>>().apply { value = emptyList() }
        try {
            if (NetworkHelper.isOnline(context)) {
                mShowProgressBar.value = true
                mShowNetworkError.value = false
                mList = repositoryNetwork.getTickers(object : NetworkResponseCallback {
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
