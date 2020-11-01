package com.example.cryptos.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.cryptos.database.Ticker
import com.example.cryptos.network.model.CryptoApiStatus
import com.example.cryptos.network.model.ResponseTickers
import com.example.cryptos.repository.CryptoRepository
import com.example.cryptos.repository.TickerDatabaseRepository
import com.example.cryptos.usecases.GetTickersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CryptoViewModel @ViewModelInject internal constructor(
    private val cryptoRepository: CryptoRepository,
    private val databaseRepository: TickerDatabaseRepository
) : ViewModel() {

    private val _status = MutableLiveData<CryptoApiStatus>()
    val status: LiveData<CryptoApiStatus>
        get() = _status

    private val _tickersResponse = MutableLiveData<ResponseTickers>()
    val newsResponse: LiveData<ResponseTickers>
        get() = _tickersResponse

    fun getTickers() {
        viewModelScope.launch {
            try {
                _status.value = CryptoApiStatus.LOADING
                _tickersResponse.value = GetTickersUseCase(cryptoRepository).invoke()
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

    fun getTickersByMarker(marker: String): LiveData<List<Ticker>> {
        return databaseRepository.getTickersByMarker(marker)
    }

    init {

    }


}