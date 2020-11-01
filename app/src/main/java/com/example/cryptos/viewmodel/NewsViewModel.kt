package com.example.cryptos.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.cryptos.network.model.NewsApiStatus
import com.example.cryptos.network.model.ResponseNews
import com.example.cryptos.repository.NewsRepository
import com.example.cryptos.usecases.GetNewsUseCase
import kotlinx.coroutines.launch

class NewsViewModel @ViewModelInject internal constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _status = MutableLiveData<NewsApiStatus>()
    val status: LiveData<NewsApiStatus>
        get() = _status

    private val _newsResponse = MutableLiveData<ResponseNews>()
    val newsResponse: LiveData<ResponseNews>
        get() = _newsResponse

    fun getNews() {
        viewModelScope.launch {
            try {
                _status.value = NewsApiStatus.LOADING
                _newsResponse.value = GetNewsUseCase(newsRepository).invoke()
                _status.value = NewsApiStatus.DONE


            } catch (e: Exception) {
                _status.value = NewsApiStatus.ERROR
                e.printStackTrace()
            }
        }
    }

    init {

    }


}