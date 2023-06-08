package com.example.test.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.test.core.BaseViewModel
import com.example.test.helper.SingleLiveEvent
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : BaseViewModel() {

    val openUrlCommand = SingleLiveEvent<Boolean>()

    private val _url: MutableLiveData<String> = MutableLiveData()
    val url: LiveData<String> = _url

    fun init() {
        if (!repository.isEmulator()) {
            viewModelScope.launch {
                repository.getUrl().collect {
                    if (it.isNotBlank())
                        _url.value = it
                    else
                        openUrlCommand.value = false
                }
            }
        } else {
            openUrlCommand.value = false
        }
    }
}