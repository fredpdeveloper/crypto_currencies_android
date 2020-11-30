package com.example.cryptos.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.cryptos.database.Ticker
import com.example.cryptos.api.model.CryptoApiStatus
import com.example.cryptos.repository.TickerRepository
import com.example.cryptos.repository.TickerDatabaseRepository
import com.example.cryptos.usecases.GetTickersDatabaseUseCase
import com.example.cryptos.usecases.GetTickersUseCase
import com.example.cryptos.usecases.InsertTickersDatabaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TickersViewModel @ViewModelInject internal constructor(
    private val tickerRepository: TickerRepository,
    private val databaseRepository: TickerDatabaseRepository
) : ViewModel() {

    private val _status = MutableLiveData<CryptoApiStatus>()
    val status: LiveData<CryptoApiStatus>
        get() = _status

    private val _tickersResponse = MutableLiveData<List<Ticker>>()
    val tickersResponse: LiveData<List<Ticker>>
        get() = _tickersResponse

    private val _tickersDatabase = MutableLiveData<List<Ticker>>()
    val tickersDatabase: LiveData<List<Ticker>>
        get() = _tickersDatabase

    private val _tickerSelect = MutableLiveData<Ticker>()
    val tickerSelect: LiveData<Ticker>
        get() = _tickerSelect

    private val _tickerPercent = MutableLiveData<Double>()
    val tickerPercent: LiveData<Double>
        get() = _tickerPercent

    fun getTickers(market: String? = null) {
        viewModelScope.launch {
            try {

                _status.value = CryptoApiStatus.LOADING
                _tickersResponse.value = GetTickersUseCase(tickerRepository).invoke(market)
                insert(_tickersResponse.value!!)
                _status.value = CryptoApiStatus.DONE


            } catch (e: Exception) {
                _status.value = CryptoApiStatus.ERROR
                e.printStackTrace()
            }
        }
    }

    private fun insert(tickers: List<Ticker>) {
        viewModelScope.launch {
            InsertTickersDatabaseUseCase(databaseRepository).invoke(tickers)
        }

    }

    fun getTickersByMarker(ticker: Ticker) {
        viewModelScope.launch {
            _tickerSelect.value = ticker
            _tickersDatabase.value =
                GetTickersDatabaseUseCase(databaseRepository).invoke(ticker.market)
            calculatePercent(_tickersDatabase.value!!, _tickerSelect.value!!)

        }
    }

    private fun calculatePercent(tickers: List<Ticker>, ticker: Ticker) {
        var sum = 0.0
        tickers.forEach {
            sum += it.bid.toDouble()
        }
        val average = sum / tickers.size
        val percent = ((ticker.bid.toDouble() * 100) / average) - 100
        _tickerPercent.value = percent

    }


    init {
    }


}