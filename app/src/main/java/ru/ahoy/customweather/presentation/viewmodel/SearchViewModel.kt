package ru.ahoy.customweather.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.java.KoinJavaComponent
import ru.ahoy.domain.models.SearchItem
import ru.ahoy.domain.usecase.SearchUseCase

class SearchViewModel(private val searchUseCase: SearchUseCase) : ViewModel() {

    companion object {
        const val SEARCH_DELAY = 300L
    }

    private var _items: MutableStateFlow<List<SearchItem>> = MutableStateFlow(listOf())
    var items: StateFlow<List<SearchItem>> = _items.asStateFlow()

    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            search(getNetworkIPAddress())
        }
    }

    private suspend fun getNetworkIPAddress(): String = withContext(Dispatchers.IO) {
        val client = KoinJavaComponent.get<OkHttpClient>(OkHttpClient::class.java)
        val request = Request.Builder().url("https://api.ipify.org?format=jsonz").build()
        val response = client.newCall(request).execute()
        return@withContext response.body?.string().orEmpty()
    }

    fun search(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DELAY)
            _items.value = searchUseCase.execute(query)
        }
    }

    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
    }
}