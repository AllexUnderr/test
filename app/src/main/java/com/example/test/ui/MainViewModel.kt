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

    val openPlugCommand = SingleLiveEvent<Boolean>()

    val showErrorPageCommand: SingleLiveEvent<Boolean> = SingleLiveEvent()

    private val _url: MutableLiveData<String> = MutableLiveData()
    val url: LiveData<String> = _url

    fun init() {
        viewModelScope.launch {
            repository.savedUrl.collect {

                if (it.isNotBlank()) {
                    openUrl(it)
                } else {
                    try {
                        saveRemoteUrl()
                    } catch (e: Exception) {
                        showErrorPageCommand.value = true
                        processError(e)
                    }
                }
            }
        }
    }

    private fun openUrl(url: String) {
        if (connection.isInternetAvailable())
            _url.value = url
        else
            showErrorPageCommand.value = true
    }

    private suspend fun saveRemoteUrl() {
        val urlToSave = repository.getRemoteUrl()
        if (urlToSave.isBlank() || repository.isEmulator()) {
            openPlugCommand.value = true
        } else {
            repository.saveUrl(urlToSave)
            _url.value = urlToSave
        }
    }
}