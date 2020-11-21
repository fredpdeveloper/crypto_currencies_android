package com.example.cryptos.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.cryptos.database.Ticker
import com.example.cryptos.api.model.CryptoApiStatus
import com.example.cryptos.repository.TickerRepository
import com.example.cryptos.repository.TickerDatabaseRepository
import com.example.cryptos.usecases.GetTickersDatabaseUseCase
import com.example.cryptos.usecases.GetTickersUseCase
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

    fun getTickers(market: String? = null) {
        viewModelScope.launch {
            try {

                _status.value = CryptoApiStatus.LOADING
                var response = GetTickersUseCase(tickerRepository).invoke().data.filter { it.bid.toDouble() > 0 }
                if (market != null) {
                    _tickersResponse.value =
                        response.filter { it.market.contains(market) }

                }else{
                    _tickersResponse.value = response
                }
                insert(response)
                _status.value = CryptoApiStatus.DONE


            } catch (e: Exception) {
                _status.value = CryptoApiStatus.ERROR
                e.printStackTrace()
            }
        }
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(tickers: List<Ticker>) = viewModelScope.launch(Dispatchers.IO) {
        databaseRepository.insert(tickers)
    }

    fun getTickersByMarker(marker: String) {
        viewModelScope.launch {
            _tickersDatabase.value = GetTickersDatabaseUseCase(databaseRepository).invoke(marker)
        }
    }

    init {
    }


}