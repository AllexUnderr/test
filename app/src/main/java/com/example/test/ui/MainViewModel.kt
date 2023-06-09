package com.example.test.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.test.core.BaseViewModel
import com.example.test.helper.SingleLiveEvent
import com.example.test.network.Connection
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MainRepository,
    private val connection: Connection
) : BaseViewModel() {

    val openUrlCommand = SingleLiveEvent<Boolean>()

    val showErrorPageCommand: SingleLiveEvent<Boolean> = SingleLiveEvent()

    private val _url: MutableLiveData<String> = MutableLiveData()
    val url: LiveData<String> = _url

    fun init() {
        if (!repository.isEmulator()) {
            viewModelScope.launch {
                try {
                    collectUrl()
                } catch (e: Exception) {
                    showErrorPageCommand.value = true
                    processError(e)
                }
            }
        } else {
            openUrlCommand.value = false
        }
    }

    private suspend fun collectUrl() {
        repository.getUrl().collect {
            if (connection.isInternetAvailable())
                setUrl(it)
            else
                showErrorPageCommand.value = true
        }
    }

    private fun setUrl(urlToSet: String) {
        if (urlToSet.isNotBlank())
            _url.value = urlToSet
        else
            openUrlCommand.value = false
    }
}